package com.example.identityservice.exception;

import com.example.identityservice.customexceptions.AuthenticationFailedException;
import com.example.identityservice.customexceptions.InvalidJwtToken;
import com.example.identityservice.customexceptions.InvalidRefreshToken;
import com.example.identityservice.customexceptions.InvalidAccessToken;
import com.example.identityservice.model.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(AuthenticationException ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        AuthenticationFailedException failedException=new AuthenticationFailedException(errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(errorMessage));
    }
    @ExceptionHandler(value = InvalidRefreshToken.class)
    public ResponseEntity<ApiResponse> handleInvalidRefreshToken(InvalidRefreshToken ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(value = InvalidJwtToken.class)
    public ResponseEntity<ApiResponse> handleInvalidJwtToken(InvalidJwtToken ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse> handleDataAccessException(DataAccessException ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(Exception.class)//
    public ResponseEntity<String> handleException(Exception ex) {
        String errorMessage= ex.getMessage();
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)//
    public ResponseEntity<String> handleConstraintViolationException(Exception ex) {
        String errorMessage="Error performing database operation";
        System.out.println("errorMessage = " + errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + errorMessage);
    }

}
