package org.example.springbootrabbitmq.service;


import jakarta.validation.Valid;
import org.example.springbootrabbitmq.payload.UserDTO;
import org.example.springbootrabbitmq.payload.UserResponseDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
}
