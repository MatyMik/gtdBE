package com.codebeforedawn.be.authentication.dto;

public record AuthenticationResponse(String accessToken, String refreshToken) {
}
