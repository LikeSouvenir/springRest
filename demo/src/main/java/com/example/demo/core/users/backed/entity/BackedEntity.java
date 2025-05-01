package com.example.demo.core.users.backet.entity;

import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
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
@Table(name = "backet_entity")
public class backetEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile", referencedColumnName = "id")
    private ProfileEntity profile;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_in_market", referencedColumnName = "id")
    private List<ProductsInMarketEntity> products_in_market = new ArrayList<>();

    // кол-во добавить
}
