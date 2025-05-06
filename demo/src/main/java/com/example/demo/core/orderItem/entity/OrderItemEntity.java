package com.example.demo.core.orderItem.entity;

import com.example.demo.core.contractor.entity.ContractorEntity;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.order.entity.OrderEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items_entity")
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "request_count")
    private int requestCount;

    @Column(name = "product_price")
    private double productPrice;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContractorEntity> contractor = new ArrayList<>();
}