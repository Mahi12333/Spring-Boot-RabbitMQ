package org.example.springbootrabbitmq.service;


import org.example.springbootrabbitmq.payload.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
}
