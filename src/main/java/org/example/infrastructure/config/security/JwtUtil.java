package org.example.infrastructure.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "bG9uZy1hbmQtcmFuZG9tLXNlY3JldC1rZXktZm9yLWp3dA=="; // Clave en Base64

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(getSigningKey()) // Firma con clave segura
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser() // ← En vez de parserBuilder()
                .verifyWith(getSigningKey()) // Para la versión 0.12.6
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}