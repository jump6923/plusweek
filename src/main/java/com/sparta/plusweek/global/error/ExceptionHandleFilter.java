package com.sparta.plusweek.global.error;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sparta.plusweek.global.error.exception.InvalidTokenException;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.sparta.plusweek.global.error.ErrorCode.INVALID_TOKEN;

public class ExceptionHandleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
        }catch (InvalidTokenException e){
            setResponse(response);
            ErrorResponse errorResponse = new ErrorResponse(INVALID_TOKEN);

            try{
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            }catch (IOException ignored){
            }
        }
    }

    private void setResponse(HttpServletResponse response) {
        response.setStatus(INVALID_TOKEN.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }
}
