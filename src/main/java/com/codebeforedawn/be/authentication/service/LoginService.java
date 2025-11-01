package com.codebeforedawn.be.authentication.service;

import com.codebeforedawn.be.authentication.JwtService;
import com.codebeforedawn.be.authentication.entity.RefreshToken;
import com.codebeforedawn.be.authentication.RefreshTokenDao;
import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import com.codebeforedawn.be.authentication.dto.LoginRequest;
import com.codebeforedawn.be.user.User;
import com.codebeforedawn.be.user.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenDao refreshTokenDao;
    private final UserDao userDao;

    protected AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        final User user = userDao.loadUserByUsername(request.email());
        String accessToken = jwtService.generateAccessToken(user.email(), Map.of());
        RefreshToken refreshToken = refreshTokenDao.createRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken.getToken());
    }
}
