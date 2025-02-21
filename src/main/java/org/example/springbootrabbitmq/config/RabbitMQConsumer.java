package org.example.springbootrabbitmq.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootrabbitmq.event.UserRegisteredEvent;
import org.example.springbootrabbitmq.service.PushNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final PushNotificationService pushNotificationService;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_NAME)
    public void consumeUserRegistrationEvent(UserRegisteredEvent event) {
        log.info("✅ Received UserRegisteredEvent: {}", event);
        // Email sending logic already handled in EmailService
    }

    @RabbitListener(queues = RabbitMQConstants.DLQ_NAME)
    public void handleDeadLetterQueueMessages(String message) {
        log.error("❌ Moved to Dead Letter Queue: {}", message);
        // TODO: Implement retry logic or send an alert
    }


    @RabbitListener(queues = RabbitMQConstants.PUSH_NOTIFICATION_QUEUE)
    public void consumePushNotificationEvent(UserRegisteredEvent event) {
        log.info("✅ Received Push Notification Event: {}", event);
        pushNotificationService.sendPushNotification(
                event.getDeviceToken(),
                "Welcome, " + event.getUsername() + "!",
                "Thank you for registering. Enjoy our services!"
        );
    }
}
