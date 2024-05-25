package com.free2go.community.configuration.jwt;

import com.free2go.community.configuration.jwt.domain.RefreshToken;
import com.free2go.community.configuration.jwt.service.RefreshTokenService;
import com.free2go.community.user.domain.Role;
import com.free2go.community.user.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    public String generateAccessToken(User user) {
        return generateToken(user, Duration.ofHours(2));
    }

    public String generateRefreshToken(User user) {
        String token = generateToken(user, Duration.ofHours(24));
        refreshTokenService.save(user, token);
        return token;
    }

    public String regenerateRefreshToken(User user, RefreshToken refreshToken) {
        refreshTokenService.deleteById(refreshToken.getId());
        return generateRefreshToken(user);
    }

    private String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
//        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "");
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey()));
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        return claims.get("id", Long.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
