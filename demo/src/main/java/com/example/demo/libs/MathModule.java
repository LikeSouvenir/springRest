package com.example.demo.libs;

import com.example.demo.utils.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MathModule {

    public String add(Integer a, Integer b) {
        throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
//        return a + b;
    }
}
