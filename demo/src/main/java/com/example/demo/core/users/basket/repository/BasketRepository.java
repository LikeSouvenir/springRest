package com.example.demo.core.users.basket.repository;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.core.users.basket.entity.BasketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {

    @Query("select new com.example.demo.controllers.markets.product.dto.ProductList(" +
            "pim.id, m.name, m.address, p.name, p.category, p.articleInRegistry, pim.cost, pim.count) " +
            "from BasketEntity b " +
            "join b.products_in_market pim " +
            "join pim.market m " +
            "join pim.product p")
    List<ProductList> findAllProducts();

    @Query("select new com.example.demo.controllers.markets.product.dto.ProductList(" +
            "pim.id,  m.name, m.address, p.name, p.category, p.articleInRegistry, pim.cost, pim.count )" +
            "from BasketEntity b " +
            "join b.products_in_market pim " +
            "join pim.market m " +
            "join pim.product p " +
            "where b.profile.id = :profileId")
    List<ProductList> findByProfileId(UUID profileId);

    @Transactional
    @Query(value = "select be from BasketEntity be " +
            "left join fetch be.products_in_market " +
            "where be.profile.id = :profileId")
    BasketEntity findEntityByProfileId(UUID profileId);

    @Modifying
    @Transactional
    @Query(value = "delete from BasketEntity b where b.profile.id = :profileId")
    void deleteByProfileId(UUID profileId);
}
