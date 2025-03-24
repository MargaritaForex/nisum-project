package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.Login;
import org.example.domain.model.User;
import org.example.domain.port.JwtServicePort;
import org.example.domain.port.UserPersistencePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserPersistencePort userPersistencePort;
    private final JwtServicePort jwtServicePort;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (isEmailAlreadyRegistered(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        String token = jwtServicePort.generateToken(user.getId());
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userPersistencePort.save(user);
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return userPersistencePort.findByEmail(email).isPresent();
    }

    public User findByEmail(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User updateUser(String email, User updatedUser) {
        User existingUser = findByEmail(email);

        existingUser.setName(updatedUser.getName());
        existingUser.setPhones(updatedUser.getPhones());
        existingUser.setModified(LocalDateTime.now());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userPersistencePort.save(existingUser);
    }

    public void deactivateUser(String email) {
        User existingUser = findByEmail(email);
        existingUser.setActive(false);
        existingUser.setModified(LocalDateTime.now());

        userPersistencePort.save(existingUser);
    }

    public User login(Login loginDto) {
        User user = findByEmail(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        user.setToken(jwtServicePort.generateToken(user.getId()));

        return userPersistencePort.save(user);
    }

    public List<User> getAllUsers() {
        return userPersistencePort.findAll();
    }
}
