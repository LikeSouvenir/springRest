package com.example.demo.controllers.order.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnValueOrderDTO {

    private UUID userId;
    private List<OrderItemDTO> products = new ArrayList<>();
    private String paymentMethod;
    private String orderStatus;
    private double cost;
    private int count;
}
