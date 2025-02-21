package org.example.springbootrabbitmq.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootrabbitmq.payload.UserDTO;
import org.example.springbootrabbitmq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
        private final Logger logger = LoggerFactory.getLogger(UserController.class);
        private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO response = userService.registerUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
