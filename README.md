# Notification Service

## Objective
A simple notification service to send notifications (Email, SMS, In-App) to users.

## Features
- Send a notification: `POST /notifications`
- Get user notifications: `GET /users/{id}/notifications`
- Supports notification types: EMAIL, SMS, IN_APP
- Uses an in-memory queue for processing and retries for failed notifications

## Setup Instructions

### Prerequisites
- Java 17+
- Maven

### Build & Run

```sh
cd notification
mvn clean install
mvn spring-boot:run
```

### API Usage

#### Send a Notification

```http
POST http://localhost:8080/notifications
Content-Type: application/json

{
  "userId": "user123",
  "type": "EMAIL",
  "content": "Hello from Notification Service!"
}
```

#### Get User Notifications

```http
GET http://localhost:8080/users/user123/notifications
```

### Assumptions
- Notifications are stored in-memory (not persisted).
- Notification delivery is simulated; actual Email/SMS sending is not implemented.
- The queue and retry logic are in-memory and for demonstration only.

### Notes
- For production, use persistent storage and a real message queue (e.g., RabbitMQ, Kafka).
- Lombok is used for boilerplate code reduction. Make sure your IDE supports Lombok.

