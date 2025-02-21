package org.example.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    // ✅ Primary Exchange (Topic Type)
    @Bean
    public TopicExchange exchange() {
        log.info("✅ RabbitMQ Exchange Created: {}", RabbitMQConstants.EXCHANGE_NAME);
        return new TopicExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    // ✅ Main Queue with Dead Letter Configuration
    @Bean
    public Queue emailQueue() {
        log.info("✅ RabbitMQ Queue Created: {}", RabbitMQConstants.QUEUE_NAME);
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", RabbitMQConstants.DLX_NAME)
                .withArgument("x-dead-letter-routing-key", RabbitMQConstants.DLQ_ROUTING_KEY)
                .build();
    }

    // ✅ Binding Queue to Exchange
    @Bean
    public Binding binding(Queue emailQueue, TopicExchange exchange) {
        log.info("✅ RabbitMQ Queue Bound: {} -> {}", RabbitMQConstants.QUEUE_NAME, RabbitMQConstants.EXCHANGE_NAME);
        return BindingBuilder.bind(emailQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY);
    }

    // ✅ Dead Letter Exchange (Direct Type)
    @Bean
    public DirectExchange deadLetterExchange() {
        log.info("✅ RabbitMQ DLX Created: {}", RabbitMQConstants.DLX_NAME);
        return new DirectExchange(RabbitMQConstants.DLX_NAME);
    }

    // ✅ Dead Letter Queue
    @Bean
    public Queue deadLetterQueue() {
        log.info("✅ RabbitMQ DLQ Created: {}", RabbitMQConstants.DLQ_NAME);
        return QueueBuilder.durable(RabbitMQConstants.DLQ_NAME).build();
    }

    // ✅ Binding DLQ to DLX
    @Bean
    public Binding deadLetterBinding() {
        log.info("✅ RabbitMQ DLQ Bound: {} -> {}", RabbitMQConstants.DLQ_NAME, RabbitMQConstants.DLX_NAME);
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(RabbitMQConstants.DLQ_ROUTING_KEY);
    }

    @Bean
    public Queue pushNotificationQueue() {
        log.info("pushNotificationQueue Created: {}", RabbitMQConstants.PUSH_NOTIFICATION_QUEUE);
        return QueueBuilder.durable(RabbitMQConstants.PUSH_NOTIFICATION_QUEUE).build();
    }

    @Bean
    public Binding pushNotificationBinding(Queue pushNotificationQueue, TopicExchange exchange) {
        log.info("pushNotificationBinding Created: {}", RabbitMQConstants.PUSH_NOTIFICATION_QUEUE);
        return BindingBuilder.bind(pushNotificationQueue)
                .to(exchange)
                .with(RabbitMQConstants.PUSH_NOTIFICATION_ROUTING_KEY);
    }

    // ✅ JSON Message Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        log.info("✅ RabbitMQ JSON Message Converter Enabled");
        return new Jackson2JsonMessageConverter();
    }

    // ✅ RabbitTemplate (for Publishing Messages)
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        log.info("✅ RabbitMQ Template Configured");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // ✅ Listener Container Factory (for Consumers)
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.info("✅ RabbitMQ Listener Factory Configured");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setDefaultRequeueRejected(false); // Prevent infinite retry loops
        return factory;
    }
}
