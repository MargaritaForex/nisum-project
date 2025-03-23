package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.User;
import org.example.domain.port.JwtServicePort;
import org.example.domain.port.UserPersistencePort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserPersistencePort userPersistencePort;
    private final JwtServicePort jwtServicePort; // 🔹 Ahora usa la interfaz

    public User registerUser(User user) {
        // Validaciones específicas de negocio
        if (isEmailAlreadyRegistered(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setId(UUID.randomUUID());
        String token = jwtServicePort.generateToken(user.getId()); // Genera el token
        user.setToken(token);

        return userPersistencePort.save(user);
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return userPersistencePort.findByEmail(email).isPresent();
    }

}
