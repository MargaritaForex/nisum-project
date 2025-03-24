package org.example.domain.service;

import org.example.domain.model.User;
import org.example.domain.port.JwtServicePort;
import org.example.domain.port.UserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final String JUAN = "Juan";
    public static final String ENCODED_PASSWORD = "encodedPassword";
    public static final String MOCKED_TOKEN = "mockedToken";
    public static final int ZERO = 0;
    public static final String MAIL = "juan@rodriguez.org";
    public static final String PASSWORD = "password123";
    public static final String PEDRO = "Pedro";
    public static final String MAIL1 = "pedro@correo.com";
    public static final String PASSWORD1 = "password456";
    public static final String EMAIL_IS_ALREADY_REGISTERED = "Email is already registered";
    @Mock
    private UserPersistencePort userRepository;
    @Mock
    private JwtServicePort jwtServicePort;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    public void whenUserIsSaved_thenSuccess() {
        User user = User.builder()
                .name(JUAN)
                .email(MAIL)
                .password(PASSWORD)
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(jwtServicePort.generateToken(any(UUID.class))).thenReturn(MOCKED_TOKEN);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(ZERO);
            savedUser.setId(UUID.randomUUID());
            savedUser.setCreated(LocalDateTime.now());
            return savedUser;
        });

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreated());
        assertEquals(JUAN, savedUser.getName());
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
        assertEquals(MOCKED_TOKEN, savedUser.getToken());

        verify(userRepository).save(any(User.class));
        verify(jwtServicePort).generateToken(any(UUID.class));
        verify(passwordEncoder).encode(anyString());
    }

    @Test
    void whenEmailAlreadyRegistered_thenThrowsException() {
        User user = User.builder()
                .name(PEDRO)
                .email(MAIL1)
                .password(PASSWORD1)
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
        assertEquals(EMAIL_IS_ALREADY_REGISTERED, exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }
}