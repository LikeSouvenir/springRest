package com.example.demo.core.cart.entity;

import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_entity")
public class CartEntity extends BaseEntity {

    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)// cart_id
    private List<ProductInCartEntity> productsInCart = new ArrayList<>();

    @Column(name = "discount")
    private int discount;
}
