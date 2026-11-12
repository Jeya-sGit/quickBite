
package com.qb.quickbite.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.qb.quickbite.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expiration;

    public JwtService(JwtProperties jwtProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = jwtProperties.getExpiration();
    }

    public String generateToken(String email) {

        Date issuedAt = new Date();
        Date expirationDate = new Date(
                issuedAt.getTime() + expiration
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(signingKey)
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {

        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
