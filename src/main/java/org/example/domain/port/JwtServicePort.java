package org.example.domain.port;

import java.util.UUID;

public interface JwtServicePort {
    String generateToken(UUID userId);
}
