package com.example.claim.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    // Secret key used for signing and verifying JWT tokens
    @Value("${app.jwt.secret}")
    private String secretKey;

    // Extracts username (subject) from the JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extracts all claims from the token by validating and parsing it
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // Convert secret key to byte array for signing
                .parseClaimsJws(token)               // Parse and validate the JWT token
                .getBody();                          // Return the JWT body (claims)
    }

    // Validates the token by checking if extracted username matches the expected username
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username);
    }
}

