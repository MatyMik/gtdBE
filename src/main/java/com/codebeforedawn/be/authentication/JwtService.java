package com.codebeforedawn.be.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key accessKey;
    private final Key refreshKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtService(
            @Value("${jwt.access.secret}") String accessSecret,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            @Value("${jwt.access.expiration}") long accessTokenExpirationMs,
            @Value("${jwt.refresh.expiration}") long refreshTokenExpirationMs) {

        this.accessKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(accessSecret));
        this.refreshKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(refreshSecret));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, accessKey, accessTokenExpirationMs);
    }

    public boolean isAccessTokenValid(String token) {
        return isTokenValid(token, accessKey);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, Map.of(), refreshKey, refreshTokenExpirationMs);
    }

    public boolean isRefreshTokenValid(String token) {
        return isTokenValid(token, refreshKey);
    }

    private String generateToken(String subject, Map<String, Object> claims, Key key, long expirationMs) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token, boolean isAccessToken) {
        Key key = isAccessToken ? accessKey : refreshKey;
        return parseToken(token, key).getBody().getSubject();
    }

    private boolean isTokenValid(String token, Key key) {
        try {
            parseToken(token, key);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parseToken(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
