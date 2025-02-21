package org.example.springbootrabbitmq.event;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private String userId;
    private String email;
    private String username;
    private String deviceToken;
}