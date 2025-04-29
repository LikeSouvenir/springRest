package com.example.demo.core.markets.product.entity;

import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product_entity")
public class ProductEntity extends BaseEntity {
//Товары
//-id в реестре
//-название
//-категория
    @Column(name = "articleInRegistry", nullable = false, unique = true)
    private String articleInRegistry;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)// enum
    private String category;
}