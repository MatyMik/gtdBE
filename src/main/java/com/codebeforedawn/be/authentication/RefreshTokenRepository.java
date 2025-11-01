package com.codebeforedawn.be.authentication;

import com.codebeforedawn.be.authentication.entity.RefreshToken;
import com.codebeforedawn.be.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
    void deleteAllByUser(UserEntity user);
}
