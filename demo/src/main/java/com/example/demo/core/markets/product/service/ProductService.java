package com.example.demo.core.markets.product.service;

import com.example.demo.controllers.markets.product.dto.ProductDTO;
import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.markets.product.dto.UpdateProductDTO;
import com.example.demo.core.markets.product.entity.ProductEntity;
import com.example.demo.core.markets.product.repository.ProductRepository;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.markets.productsInMarket.repository.ProductsInMarketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductsInMarketRepository productsInMarketRepository;

    ProductService(ProductRepository productRepository, ProductsInMarketRepository productsInMarketRepository) {
        this.productRepository = productRepository;
        this.productsInMarketRepository = productsInMarketRepository;
    }
    //productRepository////productRepository////productRepository////productRepository//
    //найти товары
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAllProducts();
    }
    public ProductEntity findProductById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
    public List<ProductEntity> findProductByNameAndCategory(String name, String category) {
        return productRepository.findProductByNameAndCategory(name, category);
    }
    public ProductEntity findProductByArticle(String article) {
        return productRepository.findProductByArticle(article);
    }
    //добавить товар
    public ProductEntity saveNewProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    //productsInMarketRepository////productsInMarketRepository////productsInMarketRepository//
    //найти товары в магазинах
    public List<ProductList> findMarketsPrice() {
        return productsInMarketRepository.findAllProducts();
    }
    public ProductsInMarketEntity findProductInMarketById(UUID market, UUID product) {
        return productsInMarketRepository.findByName(market, product);
    }
    public Optional<ProductsInMarketEntity> findById(UUID id) {
        return productsInMarketRepository.findById(id);
    }
    //добавить товар в магазинах
    public ProductsInMarketEntity addProductInMarket(ProductsInMarketEntity product) {
        return productsInMarketRepository.save(product);
    }
    //обновить товар в магазинах
    public void updateProductInMarket(UUID id, UpdateProductDTO product) {
        productsInMarketRepository.updateProduct(id, product.getCount(), product.getCost());
    }
}
