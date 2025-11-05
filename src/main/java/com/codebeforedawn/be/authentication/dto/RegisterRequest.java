package com.codebeforedawn.be.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for registering a new user")
public record RegisterRequest(
        @Schema(description = "Email address of the new user", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @Schema(description = "Password of the new user", requiredMode = Schema.RequiredMode.REQUIRED)
        String password) {
}
