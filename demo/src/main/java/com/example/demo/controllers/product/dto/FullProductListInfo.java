package com.example.demo.controllers.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullProductListInfo {
    private UUID productId;
    private UUID marketId;
    private String productName;
    private String productCategory;
    private String productArticle;
    private String marketName;
    private String marketOwner;
    private String marketAddress;
    private int currentCount;
    private double currentCost;
}
