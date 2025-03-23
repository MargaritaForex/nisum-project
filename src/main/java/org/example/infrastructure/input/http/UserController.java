package org.example.infrastructure.input.http;

import lombok.RequiredArgsConstructor;
import org.example.domain.service.UserService;
import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.example.infrastructure.input.http.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@Validated @RequestBody UserDTO userDto) {
        UserResponseDto newUser = userMapper.toDto(userService.registerUser(userMapper.toDomain(userDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}