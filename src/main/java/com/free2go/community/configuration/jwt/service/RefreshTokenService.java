package com.free2go.community.configuration.jwt.service;

import com.free2go.community.configuration.jwt.domain.RefreshToken;
import com.free2go.community.configuration.jwt.repository.RefreshTokenRepository;
import com.free2go.community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findById(Long id) {
        return refreshTokenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected id"));
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }

    public void save(User user, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .build());
    }

    public void deleteById(Long id) {
        RefreshToken refreshToken = findById(id);
        refreshTokenRepository.deleteById(refreshToken.getId());
    }

    public void deleteByUserIdAndRefreshToken(Long userId, String refreshToken) {
        refreshTokenRepository.deleteByUserIdAndRefreshToken(userId, refreshToken);
    }
}
