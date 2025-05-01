package com.example.demo.core.users.backed.repository;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.core.users.backed.entity.BackedEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BackedRepository extends JpaRepository<BackedEntity, UUID> {

    @Query("select new com.example.demo.controllers.markets.product.dto.ProductList(" +
            "pim.id, m.name, m.address, p.name, p.category, p.articleInRegistry, pim.cost, pim.count) " +
            "from BackedEntity b " +
            "join b.products_in_market pim " +
            "join pim.market m " +
            "join pim.product p")
    List<ProductList> findAllProducts();

    @Query("select new com.example.demo.controllers.markets.product.dto.ProductList(" +
            "pim.id,  m.name, m.address, p.name, p.category, p.articleInRegistry, pim.cost, pim.count )" +
            "from BackedEntity b " +
            "join b.products_in_market pim " +
            "join pim.market m " +
            "join pim.product p " +
            "where b.profile.id = :profileId")
    List<ProductList> findByProfileId(UUID profileId);

    @Transactional
    @Query(value = "select be from BackedEntity be " +
            "left join fetch be.products_in_market " +
            "where be.profile.id = :profileId")
    BackedEntity findEntityByProfileId(UUID profileId);

    @Modifying
    @Transactional
    @Query(value = "delete from BackedEntity b where b.profile.id = :profileId")
    void deleteByProfileId(UUID profileId);
}
