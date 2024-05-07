package com.free2go.community.configuration;

import com.free2go.community.user.service.UserDetailService;
import com.free2go.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailService userService;
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/login", "/signup", "/user").permitAll()
                                                                             .anyRequest().authenticated()) // 인증, 인가 설정
                .formLogin(formLogin -> formLogin.loginPage("/login")
                                                 .defaultSuccessUrl("/main"))
                .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/login")
                                                    .invalidateHttpSession(true))
                .csrf(csrfConfig -> csrfConfig.disable())
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
