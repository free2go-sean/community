package com.free2go.community.user.service;

import com.free2go.community.user.domain.User;
import com.free2go.community.user.dto.UserDto;
import com.free2go.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(UserDto.UserReq req) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(req.toEntity()).getId();
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
