package com.codebeforedawn.be.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationResponse(
        @Schema(description = "Access token", requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,
        @Schema(description = "refresh token", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String refreshToken) {
    public AuthenticationResponse withoutRefreshToken() {
        return new AuthenticationResponse(accessToken, null);
    }
}
