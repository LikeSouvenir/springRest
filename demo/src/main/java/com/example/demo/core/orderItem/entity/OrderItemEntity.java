package com.example.demo.core.orderItem.entity;

import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.order.entity.OrderEntity;
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
@Table(name = "order_items_entity")
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "count_in_order")
    private int countInOrder;

    @Column(name = "product_price")
    private double productPrice;

    @PrePersist
    protected void onCreate() {
        this.countInOrder = this.ProductEntity.getCurrentCount();
        this.productPrice = this.ProductEntity.getCurrentCost();
    }
}