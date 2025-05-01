package com.example.demo.controllers.order.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private UUID userId;
    private String paymentMethod;

    public boolean Validate() {
        if (this.userId == null) {
            return false;
        }
        if (this.paymentMethod == null||this.paymentMethod.isEmpty()) {
            this.paymentMethod = "Не установлен";
        }
        return true;
    }
}
