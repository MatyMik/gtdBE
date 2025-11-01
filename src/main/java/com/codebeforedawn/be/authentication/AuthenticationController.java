package com.codebeforedawn.be.authentication;

import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import com.codebeforedawn.be.authentication.dto.LoginRequest;
import com.codebeforedawn.be.authentication.dto.RegisterRequest;
import com.codebeforedawn.be.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody String refreshToken) {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(authenticationResponse);
    }
}
