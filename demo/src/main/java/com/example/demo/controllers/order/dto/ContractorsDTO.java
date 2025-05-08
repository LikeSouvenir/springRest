package com.example.demo.controllers.order.dto;

import com.example.demo.controllers.market.dto.MarketDTO;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorsDTO {
    private MarketDTO market;
    private int amountCount;
    private double amountPrice;
    private String status;
}
