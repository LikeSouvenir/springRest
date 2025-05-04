package com.example.demo.core.product.entity;

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
    @Column(name = "article_in_registry", nullable = false, unique = true)
    private String articleInRegistry;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)// enum
    private String category;
}