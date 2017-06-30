package com.dev9.controller;

import com.dev9.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Error> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return new ResponseEntity<>(Error.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
