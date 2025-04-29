package com.example.demo.core.markets.productsInMarket.repository;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface ProductsInMarketRepository extends JpaRepository<ProductsInMarketEntity, UUID> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE ProductsInMarketEntity SET count = count + ?2, cost = ?3 where id = ?1")
    void updateProduct(UUID Id, Integer count, Double cost);


//    @Query(value = "select pm from ProductsInMarketEntity pm where pm.market = ?1 and pm.product = ?2")
    @Query("SELECT pm FROM ProductsInMarketEntity pm WHERE pm.market.id = ?1 AND pm.product.id = ?2")
    ProductsInMarketEntity findByName(UUID market, UUID product);

    @Query(value = "select new com.example.demo.controllers.markets.product.dto.ProductList" +
            "(pme.id, m.name, m.address, p.name, p.category, p.articleInRegistry, pme.cost, pme.count) " +
            "from ProductsInMarketEntity pme " +
            "join pme.market m " +
            "join pme.product p")
    List<ProductList> findAllProducts();
}
