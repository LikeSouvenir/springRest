package com.example.demo.controllers.order.dto;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.core.profile.entity.ProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnValueOrderDTO {

    private ProfileEntity profile;
    private List<ProductList> products = new ArrayList<>();
    private String paymentMethod;
    private String status;
    private double cost;
    private int count;
}
