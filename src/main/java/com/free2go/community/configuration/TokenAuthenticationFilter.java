package com.free2go.community.configuration;

import com.free2go.community.configuration.jwt.TokenProvider;
import com.free2go.community.configuration.jwt.domain.RefreshToken;
import com.free2go.community.configuration.jwt.service.RefreshTokenService;
import com.free2go.community.user.domain.User;
import com.free2go.community.user.service.UserService;
import com.free2go.community.util.BeanUtil;
import com.free2go.community.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHENTICATION = "Authorization";
    private final static String HEADER_REFRESH = "Set-Cookie";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken=";
    private final static String REFRESH_TOKEN_SUFFIX = "; HttpOnly";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHENTICATION);

        String accessToken = getAccessToken(authorizationHeader);

        if (tokenProvider.validateToken(accessToken)) {
            setAuthenticationByToken(accessToken);
        } else {
            RefreshTokenService refreshTokenService = (RefreshTokenService) BeanUtil.getBean(RefreshTokenService.class);
            RefreshToken refreshToken = refreshTokenService.findByRefreshToken(CookieUtil.getCookieValue(request, "refreshToken"));

            if (tokenProvider.validateToken(refreshToken.getRefreshToken())) {
                UserService userService = (UserService) BeanUtil.getBean(UserService.class);

                accessToken = tokenProvider.generateAccessToken(userService.findById(refreshToken.getUserId()));
                setAuthenticationByToken(accessToken);
                response.setHeader(HEADER_AUTHENTICATION, TOKEN_PREFIX + accessToken);

                User user = userService.findById(refreshToken.getUserId());
                response.setHeader(HEADER_REFRESH, REFRESH_TOKEN_PREFIX + tokenProvider.regenerateRefreshToken(user, refreshToken) + REFRESH_TOKEN_SUFFIX);

            } else {
                throw new IllegalArgumentException("Unexpected Access and Refresh Token");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/signup", "/signin", "/upload"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);

    }

    private void setAuthenticationByToken(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }


}
