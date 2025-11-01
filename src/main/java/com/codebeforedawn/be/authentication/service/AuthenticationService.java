package com.codebeforedawn.be.authentication.service;

import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import com.codebeforedawn.be.authentication.dto.LoginRequest;
import com.codebeforedawn.be.authentication.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final LoginService loginService;
    private final RegisterService registerService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse login(LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        return registerService.register(registerRequest);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        return refreshTokenService.refreshToken(refreshToken);
    }
}
