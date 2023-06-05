package com.zerobase.hseungho.stockdevidend.global.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<?> handleCustomException(AbstractException e) {
        log.error("------ Occurred Custom Exception ------");
        log.error("exception class name -> {}", e.getClass().getSimpleName());
        log.error("exception simple message -> {}", e.getMessage());
        log.error("exception print stack trace -> ", e);
        log.error("---------------------------------------");

        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .code(e.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }

}
