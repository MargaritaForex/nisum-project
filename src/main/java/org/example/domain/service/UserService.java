package org.example.domain.service;

import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    public UserResponseDto registerUser(UserDTO userDto) {
        // Validaciones específicas de negocio
        if (isEmailAlreadyRegistered(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        userDto.setId(UUID.randomUUID());
        return mapToResponseDto(userDto);
    }

    private boolean isEmailAlreadyRegistered(String email) {
        // Lógica para verificar si el email ya está registrado
        // Esto es solo un ejemplo, deberías implementar la lógica real
        return false;
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
