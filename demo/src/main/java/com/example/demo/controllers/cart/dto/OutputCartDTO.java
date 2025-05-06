package com.example.demo.controllers.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputCartDTO {
    private UUID user;
    private List<ProductInCartDTO> productsInCart = new ArrayList<>();
    private int discount;
}
