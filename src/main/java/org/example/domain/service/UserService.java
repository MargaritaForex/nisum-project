package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.config.security.JwtUtil;
import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.example.infrastructure.output.db.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserResponseDto registerUser(UserDTO userDto) {
        // Validaciones específicas de negocio
        if (isEmailAlreadyRegistered(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        userDto.setId(UUID.randomUUID());
        String token = jwtUtil.generateToken(userDto.getId()); // Genera el token
        userDto.setToken(token);

        return mapToResponseDto(userDto);
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private UserResponseDto mapToResponseDto(UserDTO userDto) {
        return UserResponseDto.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phones(userDto.getPhones())
                .token(userDto.getToken())
                .isActive(userDto.isActive())
                .created(userDto.getCreated() != null ? userDto.getCreated().toString() : null)
                .modified(userDto.getModified() != null ? userDto.getModified().toString() : null)
                .lastLogin(userDto.getLastLogin() != null ? userDto.getLastLogin().toString() : null)
                .build();
    }
}
