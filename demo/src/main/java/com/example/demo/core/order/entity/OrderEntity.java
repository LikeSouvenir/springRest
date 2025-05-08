package com.example.demo.core.order.entity;

import com.example.demo.controllers.order.dto.ReturnValueOrderDTO;
import com.example.demo.core.orderItem.entity.OrderItemEntity;
import com.example.demo.core.profile.entity.ProfileEntity;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_entity")
public class OrderEntity extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> products = new ArrayList<>();

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "total_count")
    private int totalCount;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
        this.status = "Создан";
    }

    // mapping
    public ReturnValueOrderDTO toDTO() {
        return ReturnValueOrderDTO.builder()
                .paymentMethod(this.getPaymentMethod())
                .orderStatus(this.getStatus())
                .count(this.getTotalCount())
                .cost(this.getTotalCost())
                .userId(this.getUserId())
                .products(this.products.stream().map(OrderItemEntity::toDTO).collect(Collectors.toList()))
                .build();
    }
}
