package com.codebeforedawn.be.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CookieService {

    @Value("${app.security.jwt.refresh-cookie.max-age:7d}")
    private Duration refreshCookieMaxAge;

    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .sameSite("Strict")
                .maxAge(refreshCookieMaxAge) // 7 days
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (var cookie : request.getCookies()) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .sameSite("Strict")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
