package com.example.demo.core.cart.service;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.repository.CartRepository;
import com.example.demo.core.product.repository.ProductRepository;
import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import com.example.demo.core.productInCart.repository.ProductInCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductInCartRepository productInCartRepository;

    public CartService(CartRepository cartRepository, ProductInCartRepository productInCartRepository) {
        this.cartRepository = cartRepository;
        this.productInCartRepository = productInCartRepository;
    }

    // CartRepository //// CartRepository //// CartRepository //// CartRepository //
    // найти
    public List<ProductList> findByUserId(UUID userId) {
        return cartRepository.findByUserId(userId);
    }
    public Optional<CartEntity> findEntityByUserId(UUID userId) {
        return cartRepository.findEntityByUserId(userId);
    }
    public Optional<CartEntity> findById(UUID Id) {
        return cartRepository.findById(Id);
    }

    public List<ProductList> findAllProducts() {
        return cartRepository.findAllProducts();
    }

    // добавить
    @Transactional
    public void createNewCart(CartEntity cartEntity) {
        cartRepository.save(cartEntity);
    }
    // удаление
    @Transactional
    public void removeFromCart(UUID userUd) {
        cartRepository.deleteByUserId(userUd);
    }

    // ProductInCartRepository //// ProductInCartRepository //// ProductInCartRepository //
    // добавить
    public ProductInCartEntity addProductInCart(ProductInCartEntity productInCartEntity) {
        return this.productInCartRepository.save(productInCartEntity);
    }
}
