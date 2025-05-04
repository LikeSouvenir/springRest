package com.example.demo.core.product.service;

import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.repository.ProductRepository;
import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import com.example.demo.core.productInCart.repository.ProductInCartRepository;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.productsInMarket.repository.ProductsInMarketRepository;
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
    private final ProductInCartRepository productInCartRepository;

    ProductService(ProductRepository productRepository,
                   ProductsInMarketRepository productsInMarketRepository,
                   ProductInCartRepository productInCartRepository
    ) {
        this.productRepository = productRepository;
        this.productsInMarketRepository = productsInMarketRepository;
        this.productInCartRepository = productInCartRepository;
    }
    //productRepository////productRepository////productRepository////productRepository//
    //найти товары
    public List<ProductDTO> findAllProducts() {
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

    //productsInMarketRepository////productsInMarketRepository////productsInMarketRepository//
    //найти товары в магазинах
    public List<FullProductListInfo> findMarketsPrice() {
        return productsInMarketRepository.findAllProducts();
    }
    public Optional<ProductsInMarketEntity> findProductInMarketById(UUID market, UUID product) {
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
    public void updateProductInMarket(UUID id, int newCount, Double newCost) {
        productsInMarketRepository.updateProduct(id, newCount, newCost);
    }

    // ProductInCartRepository //// ProductInCartRepository //// ProductInCartRepository //
    //товар в корзину
    public ProductInCartEntity addProductInCart(ProductInCartEntity product) {
        return productInCartRepository.save(product);
    }
}
