package com.example.demo.controllers.markets.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
    private UUID marketId;
    private String articleInRegister;
    private double cost;
    private int count;
    private String returnStatus;

    public boolean Validate() {
        if (this.getArticleInRegister() == null || this.getArticleInRegister().isEmpty()) {
            // set default ?
            return false;
        }
        if (this.getMarketId() == null) {
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
