package com.example.demo.core.productInCart.repository;

import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductInCartRepository extends JpaRepository<ProductInCartEntity, UUID> {
}
