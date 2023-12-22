package com.sparta.plusweek.global.error;

import com.sparta.plusweek.global.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;

import static com.sparta.plusweek.global.error.ErrorCode.*;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        final ErrorResponse response = new ErrorResponse(POST_NOT_FOUND);
        return new ResponseEntity<>(response, POST_NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException e) {
        final ErrorResponse response = new ErrorResponse(INVALID_PASSWORD);
        return new ResponseEntity<>(response, INVALID_PASSWORD.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(USER_NOT_FOUND);
        return new ResponseEntity<>(response, USER_NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateUsernameException(DuplicateUsernameException e) {
        ErrorResponse response = new ErrorResponse(DUPLICATE_USERNAME);
        return new ResponseEntity<>(response, DUPLICATE_USERNAME.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse(ACCESS_DENIED);
        return new ResponseEntity<>(response, ACCESS_DENIED.getHttpStatus());
    }



    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleServerErrorException(ServerErrorException e) {
        final ErrorResponse response = new ErrorResponse(SERVER_ERROR);
        return new ResponseEntity<>(response, SERVER_ERROR.getHttpStatus());
    }
}
