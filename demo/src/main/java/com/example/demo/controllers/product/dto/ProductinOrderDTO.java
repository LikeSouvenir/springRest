package com.example.demo.controllers.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductinOrderDTO {
    private String articleInRegistry;
    private String name;
    private String category;
}