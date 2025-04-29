package com.example.demo.controllers.markets.market.dto;

import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDTO {
    private String name;
    private String owner;
    private String address;

    public boolean Validate() {

        if (this.getOwner() == null || this.getOwner().isEmpty()) {
            return false;
        }

        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }

        return true;
    }
}
