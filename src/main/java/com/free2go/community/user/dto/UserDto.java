package com.free2go.community.user.dto;


import com.free2go.community.user.domain.Role;
import com.free2go.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserReq {
        private String email;
        private String password;
        private String nickname;

        @Builder
        public UserReq(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        public User toEntity() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return User.builder()
                    .email(this.email)
                    .password(encoder.encode(this.password))
                    .nickname(this.nickname)
                    .roles(List.of(Role.USER.getKey()))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginReq {
        private String email;
        private String password;

        @Builder
        public LoginReq(String email, String password) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            this.email = email;
            this.password = encoder.encode(password);
        }
    }


}
