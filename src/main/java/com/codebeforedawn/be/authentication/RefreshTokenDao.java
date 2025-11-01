package com.codebeforedawn.be.authentication;

import com.codebeforedawn.be.authentication.entity.RefreshToken;
import com.codebeforedawn.be.user.User;
import com.codebeforedawn.be.user.UserDao;
import com.codebeforedawn.be.user.UserEntity;
import com.codebeforedawn.be.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenDao {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;
    private final UserDao userDao;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        refreshTokenRepository.deleteAllByUser(userEntity); // only one active refresh token per user

        RefreshToken refreshToken = new RefreshToken(
                userEntity,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken createRefreshToken(String userName) {
        User user = userDao.loadUserByUsername(userName);
        return createRefreshToken(user);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please log in again.");
        }
        return token;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken); // optional: revoke immediately
            throw new RuntimeException("Refresh token has expired");
        }

        return refreshToken;
    }

    public void deleteByUser(UserEntity user) {
        refreshTokenRepository.deleteAllByUser(user);
    }
}
