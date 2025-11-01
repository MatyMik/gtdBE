package com.codebeforedawn.be.authentication.service;

import com.codebeforedawn.be.authentication.JwtService;
import com.codebeforedawn.be.authentication.entity.RefreshToken;
import com.codebeforedawn.be.authentication.RefreshTokenDao;
import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final JwtService jwtService;
    private final RefreshTokenDao refreshTokenDao;

    protected AuthenticationResponse refreshToken(String refreshToken) {
        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String username = jwtService.extractUsername(refreshToken, false);
        String newAccessToken = jwtService.generateAccessToken(username, java.util.Map.of());
        RefreshToken newRefreshToken = refreshTokenDao.createRefreshToken(username);
        return new AuthenticationResponse(newAccessToken, newRefreshToken.getToken());
    }
}
