package com.sparta.plusweek.domain.jwt;

import java.io.IOException;
import java.util.List;

import com.sparta.plusweek.global.error.exception.InvalidTokenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j(topic = "JWT 인증")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException,
            IOException {

        String tokenValue = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);
        log.info("tokenValue : {}", tokenValue);

        if (!StringUtils.hasText(tokenValue)) {
            filterChain.doFilter(request, response);
            return;
        }

        tokenValue = jwtUtil.substringToken(tokenValue);

        if (!jwtUtil.isTokenValid(tokenValue)) {
            log.error("토큰 에러");
            throw new InvalidTokenException();
        }

        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        setAuthentication(info.getSubject());

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        List<String> excludePath = List.of("/api/users/signup", "/api/users/login");
        String path = request.getRequestURI();

        return excludePath.stream().anyMatch(path::startsWith);
    }
}
