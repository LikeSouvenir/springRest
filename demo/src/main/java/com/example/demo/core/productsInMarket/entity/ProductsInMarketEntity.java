package com.example.demo.core.productsInMarket.entity;

import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_in_market_entity")
public class ProductsInMarketEntity extends BaseEntity {

    @Column(name = "current_count")
    private int currentCount;

    @Column(name = "current_cost")
    private double currentCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    private MarketEntity market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;
}