package com.example.demo.controllers.cart;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.controllers.cart.dto.InputCartDTO;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // cartService //// cartService //// cartService //// cartService //// cartService //// cartService //
    @PostMapping("/cart/addProducts")
    public ResponseEntity<CartEntity> addProduct(@RequestBody List<InputCartDTO> newProducts) {
        logger.info("cart add products");

        if (newProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        cartService.addProductInCart(newProducts);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/cart/get")
    public ResponseEntity<List<ProductList>> getProduct(@RequestParam UUID userId) {
        return ResponseEntity.ok(this.cartService.findListByUserId(userId));
    }

    @GetMapping("/cart/getAll")
    public ResponseEntity<List<ProductList>> getAll() {
        return ResponseEntity.ok(this.cartService.findAllProducts());
    }

    @DeleteMapping("/cart/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam UUID userId) {
        cartService.removeFromCart(userId);
        return ResponseEntity.ok().body(null);
    }
}
