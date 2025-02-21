package org.example.springbootrabbitmq.mapper;

import org.example.springbootrabbitmq.model.User;
import org.example.springbootrabbitmq.payload.UserDTO;

public class UserMapper {

       public static UserDTO toDTO(User user){
        return UserDTO.builder()
                .phone(user.getPhone())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .address(user.getAddress())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        user.setPhone(userDTO.getPhone());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }
}
