package com.codebeforedawn.be.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record LogoutRequest(
        @Schema(description = "UserId we want to logout", requiredMode = RequiredMode.REQUIRED)
        String userId
) {
}
