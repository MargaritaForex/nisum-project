package org.example.infrastructure.input.http.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Builder
@Data
public class UserDTO {
    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, una letra y un número")
    private String password;
    private List<PhoneDTO> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
}