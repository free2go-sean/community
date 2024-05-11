package com.free2go.community.user.service;

import com.free2go.community.configuration.jwt.TokenProvider;
import com.free2go.community.configuration.jwt.service.RefreshTokenService;
import com.free2go.community.user.domain.User;
import com.free2go.community.user.dto.UserDto;
import com.free2go.community.user.repository.UserRepository;
import com.free2go.community.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final static String HEADER_AUTHENTICATION = "Authorization";
    private final static String HEADER_REFRESH = "Set-Cookie";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken=";
    private final static String REFRESH_TOKEN_SUFFIX = "; HttpOnly";
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Long save(UserDto.UserReq req) {
        return userRepository.save(req.toEntity()).getId();
    }

    public void signin(HttpServletResponse response, UserDto.LoginReq req) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.generateAccessToken((User) authentication.getPrincipal());
        response.setHeader(HEADER_AUTHENTICATION, TOKEN_PREFIX + accessToken);

        String refreshToken = tokenProvider.generateRefreshToken((User) authentication.getPrincipal());
        response.setHeader(HEADER_REFRESH, REFRESH_TOKEN_PREFIX + refreshToken + REFRESH_TOKEN_SUFFIX);
    }

    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTHENTICATION);
        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        if (tokenProvider.getUserId(accessToken).equals(tokenProvider.getUserId(refreshToken))) {
            refreshTokenService.deleteByUserIdAndRefreshToken(tokenProvider.getUserId(accessToken), refreshToken);
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("unexpected user"));
    }
}
