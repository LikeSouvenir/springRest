package com.example.demo.controllers.markets.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private UUID id;
    private String marketName;
    private String marketAddress;
    private String productName;
    private String productCategory;
    private String productArticle;
    private double cost;
    private int count;

    public boolean Validate() {
        if (this.getMarketName() == null || this.getMarketName().isEmpty()) {
            return false;
        }
        if (this.getProductArticle() == null || this.getProductArticle().isEmpty()) {
            return false;
        }
        if (this.getProductCategory() == null || this.getProductCategory().isEmpty()) {
            return false;
        }
        if (this.getProductName() == null || this.getProductName().isEmpty()) {
            return false;
        }
        if (this.getCost() < 0) {
            return false;
        }
        if (this.getCount() < 0) {
            return false;
        }
        return true;
    }
}
