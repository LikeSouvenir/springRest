package com.example.demo.core.product.repository;


import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.controllers.product.dto.ProductinOrderDTO;
import com.example.demo.core.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "select new com.example.demo.controllers.product.dto.ProductinOrderDTO(articleInRegistry, name, category) from ProductEntity")
    List<ProductinOrderDTO> findAllProducts();

    @Query(value = "select pe from ProductEntity pe where pe.name = ?1 and pe.category = ?2")
    List<ProductEntity> findProductByNameAndCategory(String name, String category);

    @Query(value = "select pe from ProductEntity pe where pe.articleInRegistry = ?1")
    Optional<ProductEntity> findProductByArticle(String article);
}
