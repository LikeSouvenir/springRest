package com.example.demo.core.markets.productsInMarket.entity;

import com.example.demo.core.markets.market.entity.MarketEntity;
import com.example.demo.core.markets.product.entity.ProductEntity;
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

    @Column(name = "count")
    private int count;

    @Column(name = "cost")
    private double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market", referencedColumnName = "id")
    private MarketEntity market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", referencedColumnName = "id")
    private ProductEntity product;
}