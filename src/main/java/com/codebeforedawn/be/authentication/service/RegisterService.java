package com.codebeforedawn.be.authentication.service;

import com.codebeforedawn.be.authentication.JwtService;
import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import com.codebeforedawn.be.authentication.dto.RegisterRequest;
import com.codebeforedawn.be.user.CustomUserDetailService;
import com.codebeforedawn.be.user.User;
import com.codebeforedawn.be.user.UserDao;
import com.codebeforedawn.be.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RegisterService {
    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    protected AuthenticationResponse register(RegisterRequest registerRequest) {
        if(userDao.existsByEmail(registerRequest.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.password()); // In a real application, hash the password here
        User user = userDao.save(
                User.builder()
                        .email(registerRequest.email())
                        .password(hashedPassword)
                        .build()
        );
        String accessToken = jwtService.generateAccessToken(user.email(), Map.of());
        String refreshToken = jwtService.generateRefreshToken(user.email());
        return new AuthenticationResponse(accessToken, refreshToken);
    }
}
