package com.example.demo.controllers.users.backed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BackedDTO {
    private UUID profile;
    private UUID products_in_market;

    public boolean Validate() {
        if (profile == null) {
            return false;
        }

        if (products_in_market == null) {
            return false;
        }

        return true;
    }
}
