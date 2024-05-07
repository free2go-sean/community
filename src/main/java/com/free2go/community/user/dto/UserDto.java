package com.free2go.community.user.dto;


import com.free2go.community.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDto {

    @Getter
    public static class UserReq {
        private String email;
        private String password;

        @Builder
        public UserReq(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public User toEntity() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return User.builder()
                    .email(this.email)
                    .password(encoder.encode(this.password))
                    .build();
        }
    }

}
