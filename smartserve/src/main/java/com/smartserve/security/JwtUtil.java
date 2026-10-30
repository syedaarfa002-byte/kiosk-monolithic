package com.smartserve.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.smartserve.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "SmartServeSecretKeyForJwtAuthentication2026";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractClaims(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            throw new InvalidTokenException("Invalid JWT token");
        }
    }
}