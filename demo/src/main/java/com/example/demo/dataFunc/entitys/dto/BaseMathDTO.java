package com.example.demo.dataFunc.entitys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BaseMathDTO {
    private float a;
    private float b;
    private String operator;
    private float result;

}