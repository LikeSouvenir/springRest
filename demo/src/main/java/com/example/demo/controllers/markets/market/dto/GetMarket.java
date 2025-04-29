package com.example.demo.controllers.markets.market.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMarket {
    UUID id;
    private String name;
    private String owner;
    private String address;
}
