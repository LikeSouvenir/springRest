package com.example.demo.libs;

import com.example.demo.utils.exceptions.ApplicationException;
import com.example.demo.utils.responses.ApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(ErrorInterceptor.class);

    @ExceptionHandler
    public ResponseEntity<ApplicationResponse<String>> handleException(ApplicationException ex) {
        logger.error(ex.getMessage(), ex);

        return new ResponseEntity<>(new ApplicationResponse<>(ex.getStatus().value(), false, ex.getMessage()), ex.getStatus());
    }
}
