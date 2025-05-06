package com.example.demo.controllers.cart.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class InputCartDTO {
    private UUID userId;
    private UUID productId;
    private int count;
    private String status;

    public boolean Validate() {
        if (userId == null) {
            return false;
        }

        if (productId == null) {
            return false;
        }
        if (count < 0) {
            return false;
        }

        return true;
    }
}
