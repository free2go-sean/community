package com.free2go.community.configuration.jwt.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {


    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreateAccessTokenResponse {
        private String accessToken;

        @Builder
        public CreateAccessTokenResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreateAccessTokenRequest {
        private String refreshToken;

        @Builder
        public CreateAccessTokenRequest(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}

