package com.arunbalachandran.ehealth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException exception) {
        log.info("Intercepted ExpiredJWTException: " + exception.getMessage());
        return new ResponseEntity<>(
                ApiExceptionDTO.builder()
                        .message(exception.getMessage())
                        .throwable(exception)
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        log.info("Intercepted ApiException: " + exception.getMessage());
        return new ResponseEntity<>(
                ApiExceptionDTO.builder()
                        .message(exception.getMessage())
                        .throwable(exception)
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }
}
