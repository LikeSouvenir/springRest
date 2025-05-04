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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_entity")
public class CartEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductInCartEntity> productsInCart = new ArrayList<>();

    @Column(name = "discount")
    private int discount;
}
