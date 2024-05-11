package com.free2go.community.configuration.jwt.service;

import com.free2go.community.configuration.jwt.TokenProvider;
import com.free2go.community.configuration.jwt.domain.RefreshToken;
import com.free2go.community.user.domain.User;
import com.free2go.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected Token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateAccessToken(user);
    }
}
