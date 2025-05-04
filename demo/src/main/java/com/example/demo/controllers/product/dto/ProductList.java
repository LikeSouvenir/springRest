package com.example.demo.controllers.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private UUID productId;
    private String productName;
    private String productCategory;
    private String productArticle;
    private int requestCount;

    public boolean Validate() {
        if (this.getProductArticle() == null || this.getProductArticle().isEmpty()) {
            return false;
        }
        if (this.getProductCategory() == null || this.getProductCategory().isEmpty()) {
            return false;
        }
        if (this.getProductName() == null || this.getProductName().isEmpty()) {
            return false;
        }
        if (this.getRequestCount() < 0) {
            return false;
        }
        return true;
    }
}
