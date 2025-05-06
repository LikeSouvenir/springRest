package com.example.demo.core.cart.service;

import com.example.demo.controllers.cart.dto.InputCartDTO;
import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.repository.CartRepository;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.service.ProductService;
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
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductInCartRepository productInCartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productInCartRepository = productInCartRepository;
        this.productService = productService;
    }

    // CartRepository //// CartRepository //// CartRepository //// CartRepository //
    // найти
    public List<ProductList> findListByUserId(UUID userId) {
        return cartRepository.findListByUserId(userId);
    }

    public Optional<CartEntity> findEntityByUserId(UUID userId) {
        return cartRepository.findByUserId(userId);
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
    public List<InputCartDTO> addProductInCart(List<InputCartDTO> newProducts) {

        for (InputCartDTO cartDTO : newProducts) {

            if (!cartDTO.Validate()) {
                cartDTO.setStatus("Не валидно");
                continue;
            }

            Optional<CartEntity> userCart = this.findEntityByUserId(cartDTO.getUserId());
            if (userCart.isEmpty()) {
                cartDTO.setStatus("Корзина не найдена");
                continue;
            }
            Optional<ProductEntity> productInCart = this.productService.findEntityProductById(cartDTO.getProductId());
            if (productInCart.isEmpty()) {
                cartDTO.setStatus("Товар не найден");
                continue;
            }

            this.productInCartRepository.save(new ProductInCartEntity(userCart.get(), productInCart.get(), cartDTO.getCount()));
            cartDTO.setStatus("Успешно добавлен");
        }

        return newProducts;
    }
}
