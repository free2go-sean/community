package com.free2go.community.user.controller;

import com.free2go.community.user.dto.UserDto;
import com.free2go.community.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody final UserDto.UserReq req) {
        userService.save(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(HttpServletResponse response, @RequestBody final UserDto.LoginReq req) {
        userService.signin(response, req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // 뭘 해야 하는거지?
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("well work", HttpStatus.OK);
    }
}
