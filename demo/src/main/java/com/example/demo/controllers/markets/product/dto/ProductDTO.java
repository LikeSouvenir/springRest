package com.example.demo.controllers.markets.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private String articleInRegistry;
    private String name;
    private String category;

    public boolean Validate() {
        if (this.getArticleInRegistry() == null || this.getArticleInRegistry().isEmpty()) {
            // set default
            return false;
        }

        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }

        if (this.getCategory() == null || this.getCategory().isEmpty()) {
            return false;
        }

        return true;
    }
}