package com.example.demo.core.markets.product.repository;


import com.example.demo.controllers.markets.product.dto.ProductDTO;
import com.example.demo.core.markets.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "select new com.example.demo.controllers.markets.product.dto.ProductDTO(articleInRegistry, name, category) from ProductEntity")
    List<ProductDTO> findAllProducts();

    @Query(value = "select pe from ProductEntity pe where pe.name = ?1 and pe.category = ?2")
    List<ProductEntity> findProductByNameAndCategory(String name, String category);

    @Query(value = "select pe from ProductEntity pe where pe.articleInRegistry = ?1")
    ProductEntity findProductByArticle(String article);
}
