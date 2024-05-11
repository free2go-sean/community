package com.free2go.community.configuration.jwt.controller;

import com.free2go.community.configuration.jwt.dto.TokenDto;
import com.free2go.community.configuration.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<TokenDto.CreateAccessTokenResponse> createNewAccessToken(@RequestBody final TokenDto.CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TokenDto.CreateAccessTokenResponse.builder()
                        .accessToken(newAccessToken)
                        .build());
    }
}
