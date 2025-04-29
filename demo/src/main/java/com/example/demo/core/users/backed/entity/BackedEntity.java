package com.example.demo.core.users.backed.entity;

import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.users.profile.entity.ProfileEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "backed_entity")
public class BackedEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "profile", referencedColumnName = "id")
    private ProfileEntity profile;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_in_market", referencedColumnName = "id")
    private List<ProductsInMarketEntity> products_in_market;
}
