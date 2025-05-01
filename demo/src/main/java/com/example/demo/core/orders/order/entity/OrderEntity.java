package com.example.demo.core.orders.order.entity;

import com.example.demo.core.orders.orderItem.entity.OrderItemEntity;
import com.example.demo.core.users.profile.entity.ProfileEntity;
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
@Table(name = "order_entity")
public class OrderEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profile;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> products = new ArrayList<>();

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "total_count")
    private int totalCount;

    @PrePersist
    protected void onCreate() {
        this.status = "Создан";
    }
}
