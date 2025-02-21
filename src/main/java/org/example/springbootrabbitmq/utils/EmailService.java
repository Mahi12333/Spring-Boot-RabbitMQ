package org.example.springbootrabbitmq.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootrabbitmq.config.RabbitMQConstants;
import org.example.springbootrabbitmq.event.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;


    @RabbitListener(queues = RabbitMQConstants.QUEUE_NAME)
    public void sendWelcomeEmail(UserRegisteredEvent event) {
        log.info("📨 Processing email for: {}", event.getEmail());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getEmail());
            message.setSubject("Welcome, " + event.getUsername() + "!");
            message.setText("Thank you for registering. Enjoy our services!");

            mailSender.send(message);
            log.info("✅ Email sent successfully to: {}", event.getEmail());

        } catch (Exception e) {
            log.error("❌ Email sending failed: {}", e.getMessage());
            throw new RuntimeException(e); // Moves to DLQ if failure occurs
        }
    }
}
