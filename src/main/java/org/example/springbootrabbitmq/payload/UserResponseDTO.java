package org.example.springbootrabbitmq.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class UserResponseDTO {
    public List<UserDTO> content;
}
