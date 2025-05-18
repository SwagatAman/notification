package com.example.api.notification;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class NotificationService {
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();
    private final BlockingQueue<Notification> queue = new LinkedBlockingQueue<>();
    private final int MAX_RETRIES = 3;

    public NotificationService() {
        // Start a background worker for processing notifications
        new Thread(this::processQueue).start();
    }

    public void sendNotification(Notification notification) {
        notification.setStatus("PENDING");
        queue.offer(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return userNotifications.getOrDefault(userId, Collections.emptyList());
    }

    private void processQueue() {
        while (true) {
            try {
                Notification notification = queue.take();
                boolean sent = false;
                int attempts = 0;
                while (!sent && attempts < MAX_RETRIES) {
                    attempts++;
                    sent = trySend(notification);
                    if (!sent) {
                        Thread.sleep(500); // wait before retry
                    }
                }
                notification.setStatus(sent ? "SENT" : "FAILED");
                userNotifications.computeIfAbsent(notification.getUserId(), k -> new ArrayList<>()).add(notification);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private boolean trySend(Notification notification) {
        // Simulate sending logic (always succeed for IN_APP, random for others)
        if (notification.getType() == NotificationType.IN_APP) return true;
        return Math.random() > 0.2; // 80% success rate
    }
}
