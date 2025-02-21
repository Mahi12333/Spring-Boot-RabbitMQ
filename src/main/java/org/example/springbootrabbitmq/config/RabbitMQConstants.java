package org.example.springbootrabbitmq.config;


public class RabbitMQConstants {

    public static final String EXCHANGE_NAME = "user.exchange";
    public static final String QUEUE_NAME = "user.email.queue";
    public static final String ROUTING_KEY = "user.email.routingKey";

    public static final String DLX_NAME = "dead-letter-exchange";
    public static final String DLQ_NAME = "user.email.deadLetterQueue";
    public static final String DLQ_ROUTING_KEY = "user.email.dlq";

    // Push Notification Queue
    public static final String PUSH_NOTIFICATION_QUEUE = "user.push.queue";
    public static final String PUSH_NOTIFICATION_ROUTING_KEY = "user.push.routingKey";

}
