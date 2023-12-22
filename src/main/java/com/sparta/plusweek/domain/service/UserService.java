package com.sparta.plusweek.domain.service;

import org.springframework.security.core.Authentication;
import com.sparta.plusweek.domain.dto.CustomUserDetails;
import com.sparta.plusweek.domain.dto.LoginRequestDto;
import com.sparta.plusweek.domain.dto.UserSignUpRequestDto;
import com.sparta.plusweek.domain.entity.User;
import com.sparta.plusweek.domain.jwt.JwtUtil;
import com.sparta.plusweek.domain.repository.UserRepository;
import com.sparta.plusweek.global.error.exception.DuplicateUsernameException;
import com.sparta.plusweek.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원 가입
    // @param requestDto username, password 를 가지는 DTO
    @Transactional
    public void signup(UserSignUpRequestDto requestDto) {

        String username = requestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User createdUser = createUser(username, encodedPassword);

        userRepository.save(createdUser);
    }

    private User createUser(String username, String encodedPassword) {
        checkExistingUsername(username);
        return User.createUser(username, encodedPassword);
    }

    private void checkExistingUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    public String login(LoginRequestDto requestDto) {

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(UserNotFoundException::new);

        Authentication authentication = getAuthentication(requestDto.getPassword(), user);
        setAuthentication(authentication);

        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    private Authentication getAuthentication(String rawPassword, User user) {
        validatePassword(rawPassword, user.getPassword());

        CustomUserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserNotFoundException();
        }
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
