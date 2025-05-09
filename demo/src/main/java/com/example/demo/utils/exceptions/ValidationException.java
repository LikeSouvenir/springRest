package com.example.demo.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {
    private final HttpStatus status;
    public ValidationException(HttpStatus status, String message) {
        super("ValidationException: " +message);
        this.status = status;
    }
}
