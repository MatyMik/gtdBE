package com.codebeforedawn.be.user;

import lombok.Builder;
import java.util.List;

@Builder
public record User(Long id, String email, String password, List<String> roles) {
}
