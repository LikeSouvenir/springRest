package com.example.demo.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public DataNotFoundException(HttpStatus status, String message) {
        super("DataNotFoundException: " + message);
        this.status = status;
    }
}
