package com.free2go.community.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                        frameOptionsConfig -> frameOptionsConfig.disable()
                ))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/", "/login/**").permitAll()
                                .requestMatchers("/posts/**", "/v1/posts/**").hasRole(Role.USER.name())
                                .anyRequest().authenticated()
                );


        return http.build();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint = ((request, response, authException) -> {
        ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = new ObjectMapper().writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    });

    private final AccessDeniedHandler accessDeniedHandler = ((request, response, accessDeniedException) -> {
        ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = new ObjectMapper().writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    });

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {
        private final HttpStatus httpStatus;
        private final String message;
    }

}
