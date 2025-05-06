package com.example.demo.controllers.cart.dto;

import com.example.demo.core.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDTO {
    private ProductEntity product;
    private int requestCount;
}
