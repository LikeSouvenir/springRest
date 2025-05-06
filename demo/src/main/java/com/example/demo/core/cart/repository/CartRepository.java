package com.example.demo.core.cart.repository;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    @Query(value = """ 
            select new com.example.demo.controllers.product.dto.ProductList(
                p.id, p.name, p.category, p.articleInRegistry, pim.requestCount)
            from CartEntity ce
            join ce.productsInCart pim
            join pim.product p
            """)
    List<ProductList> findAllProducts();

    @Query(value = """ 
            select new com.example.demo.controllers.product.dto.ProductList(
                p.id, p.name, p.category, p.articleInRegistry, pim.requestCount)
            from CartEntity ce
            join ce.productsInCart pim
            join pim.product p
            where ce.user.id = :userId
            """)
    List<ProductList> findListByUserId(UUID userId);


    Optional<CartEntity> findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query(value = "delete from CartEntity b where b.user = :userId")
    void deleteByUserId(UUID userId);
}
