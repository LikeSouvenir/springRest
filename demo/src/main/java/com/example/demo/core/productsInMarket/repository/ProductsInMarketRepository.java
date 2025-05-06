package com.example.demo.core.productsInMarket.repository;

import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductsInMarketRepository extends JpaRepository<ProductsInMarketEntity, UUID> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE ProductsInMarketEntity SET currentCount = currentCount + ?2, currentCost = ?3 where id = ?1")
    void updateProduct(UUID Id, Integer count, Double cost);


    @Query("SELECT pm FROM ProductsInMarketEntity pm WHERE pm.market.id = ?1 AND pm.product.id = ?2")
    Optional<ProductsInMarketEntity> findByName(UUID market, UUID product);

    @Query(value = """
            select new com.example.demo.controllers.product.dto.FullProductListInfo
            (p.id, m.id, p.name, p.category, p.articleInRegistry, m.name, m.owner,
                         m.address, pme.currentCount, pme.currentCost)
            from ProductsInMarketEntity pme
            join pme.market m
            join pme.product p
            """)
    List<FullProductListInfo> findAllProducts();

    @EntityGraph(attributePaths = {"market", "product"})
    @Query(value = """
            SELECT pim FROM ProductsInMarketEntity pim
                where pim.product.id = ?1
                order by pim.currentCost DESC
            """)
    List<ProductsInMarketEntity> findByProductId(UUID productId);

    @Modifying
    @Query(value = "update ProductsInMarketEntity pim set pim.currentCount = ?2 where pim.id = ?1")
    Integer updateCount(UUID productInMarketId, int newCount);
}
