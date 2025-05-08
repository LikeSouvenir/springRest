package com.example.demo.controllers.order.dto;


import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.core.contractor.entity.ContractorEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private ProductDTO product;
    private int requestCount;
    private double productPrice;
    private List<ContractorsDTO> contractor = new ArrayList<>();
}
