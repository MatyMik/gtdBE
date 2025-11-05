package com.codebeforedawn.be.authentication;

import com.codebeforedawn.be.authentication.dto.AuthenticationResponse;
import com.codebeforedawn.be.authentication.dto.LoginRequest;
import com.codebeforedawn.be.authentication.dto.RegisterRequest;
import com.codebeforedawn.be.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name="Authentication", description="Endpoints for user authentication and registration")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.login(loginRequest);
        cookieService.addRefreshTokenCookie(response, authenticationResponse.refreshToken());
        return ResponseEntity.ok(authenticationResponse.withoutRefreshToken());
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with email, password, and confirmPassword validation",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        cookieService.addRefreshTokenCookie(response, authenticationResponse.refreshToken());
        return ResponseEntity.ok(authenticationResponse.withoutRefreshToken());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody String refreshToken, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(refreshToken);
        cookieService.addRefreshTokenCookie(response, authenticationResponse.refreshToken());
        return ResponseEntity.ok(authenticationResponse.withoutRefreshToken());
    }
}
