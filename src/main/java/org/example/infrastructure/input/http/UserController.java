package org.example.infrastructure.input.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.domain.service.UserService;
import org.example.infrastructure.input.http.dto.request.LoginDTO;
import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.example.infrastructure.input.http.mapper.UserMapper;
import org.example.infrastructure.input.http.mapper.LoginMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginMapper loginMapper;

    @Operation(summary = "Registrar un usuario", description = "Registra un usuario con email y contraseña.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "409", description = "Email ya registrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@Validated @RequestBody UserDTO userDto) {
        UserResponseDto newUser = userMapper.toDto(userService.registerUser(userMapper.toDomain(userDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.toDtoList(userService.getAllUsers()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email) {
        UserResponseDto user = userMapper.toDto(userService.findByEmail(email));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String email,
            @Validated @RequestBody UserDTO userDto) {
        UserResponseDto updatedUser = userMapper.toDto(userService.updateUser(email, userMapper.toDomain(userDto)));
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deactivateUser(@PathVariable String email) {
        userService.deactivateUser(email);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario desactivado exitosamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginDTO loginDto) {
        UserResponseDto userResponse = userMapper.toDto(userService.login(loginMapper.toDomain(loginDto)));
        return ResponseEntity.ok(userResponse);
    }

}