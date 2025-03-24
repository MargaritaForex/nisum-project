package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.User;
import org.example.domain.port.JwtServicePort;
import org.example.domain.port.UserPersistencePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserPersistencePort userPersistencePort;
    private final JwtServicePort jwtServicePort;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (isEmailAlreadyRegistered(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        String token = jwtServicePort.generateToken(user.getId());
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userPersistencePort.save(user);
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return userPersistencePort.findByEmail(email).isPresent();
    }

}
