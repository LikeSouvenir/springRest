package com.example.demo.core.orders.orderItem.entity;

import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.orders.order.entity.OrderEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "products_in_market_id")
    private ProductsInMarketEntity productInMarket;

    @Column(name = "count_in_order")
    private int countInOrder;

    @Column(name = "product_price")
    private double productPrice;

    @PrePersist
    protected void onCreate() {
        this.countInOrder = this.productInMarket.getCount();
        this.productPrice = this.productInMarket.getCost();
    }
}