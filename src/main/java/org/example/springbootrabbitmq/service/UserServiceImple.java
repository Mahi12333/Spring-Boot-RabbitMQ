package org.example.springbootrabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootrabbitmq.config.RabbitMQConstants;
import org.example.springbootrabbitmq.event.UserRegisteredEvent;
import org.example.springbootrabbitmq.mapper.UserMapper;
import org.example.springbootrabbitmq.model.User;
import org.example.springbootrabbitmq.payload.UserDTO;
import org.example.springbootrabbitmq.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService {
      private final RabbitTemplate rabbitTemplate;
      private final UserRepository userRepository;
      //private final ObjectMapper objectMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);

        UserRegisteredEvent event = new UserRegisteredEvent(
                savedUser.getId().toString(),
                userDTO.getEmail(),
                userDTO.getUsername(),
                userDTO.getDeviceToken()
        );

        // ✅ Send event as Object, not as JSON string
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE_NAME,
                RabbitMQConstants.ROUTING_KEY,
                event
        );

        // Send Push Notification Event
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE_NAME,
                RabbitMQConstants.PUSH_NOTIFICATION_ROUTING_KEY,
                event
        );

        log.info("✅ Sent Email event: {}", event);
        log.info("✅ Sent Notification event: {}", event);
        return UserMapper.toDTO(savedUser);
    }
}
