package com.sparta.plusweek.domain.controller;

import com.sparta.plusweek.domain.dto.MessageDto;
import com.sparta.plusweek.domain.dto.UserSignUpRequestDto;
import com.sparta.plusweek.domain.dto.LoginRequestDto;
import com.sparta.plusweek.domain.jwt.JwtUtil;
import com.sparta.plusweek.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?>signup(@Valid @RequestBody UserSignUpRequestDto requestDto){
            userService.signup(requestDto);
        return ResponseEntity.ok().build();
        }
    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody LoginRequestDto requestDto){
        String token = userService.login(requestDto);
        return ResponseEntity.ok()
                .header(JwtUtil.AUTHORIZATION_HEADER, token)
                .build();
    }
}

