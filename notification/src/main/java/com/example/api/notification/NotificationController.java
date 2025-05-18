package com.example.api.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notifications")
    public String sendNotification(@RequestBody Notification notification) {
        notification.setId(UUID.randomUUID().toString());
        notificationService.sendNotification(notification);
        return "Notification queued for delivery";
    }

    @GetMapping("/users/{id}/notifications")
    public List<Notification> getUserNotifications(@PathVariable("id") String userId) {
        return notificationService.getUserNotifications(userId);
    }
}
