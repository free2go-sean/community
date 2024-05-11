package com.free2go.community.configuration.jwt.repository;

import com.free2go.community.configuration.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUserIdAndRefreshToken(Long userId, String refreshToken);
}
