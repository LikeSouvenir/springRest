package com.example.demo.core.product.service;

import com.example.demo.controllers.product.dto.ProductinOrderDTO;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //найти товары
    public List<ProductinOrderDTO> findAllProducts() {
        return productRepository.findAllProducts();
    }

    public Optional<ProductEntity> findEntityProductById(UUID id) {
        return productRepository.findById(id);
    }

    public List<ProductEntity> findEntityProductByNameAndCategory(String name, String category) {
        return productRepository.findProductByNameAndCategory(name, category);
    }

    public Optional<ProductEntity> findEntityProductByArticle(String article) {
        return productRepository.findProductByArticle(article);
    }

    //добавить товар
    public ProductEntity saveNewProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
}
