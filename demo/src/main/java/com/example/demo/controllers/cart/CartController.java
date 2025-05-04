package com.example.demo.controllers.cart;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.controllers.cart.dto.CartDTO;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.service.CartService;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.service.ProductService;
import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    // cartService //// cartService //// cartService //// cartService //// cartService //// cartService //
    // Работа с товарами в корзине //
    @PostMapping("/cart/addProducts")
    public ResponseEntity<CartEntity> addProduct(@RequestBody List<CartDTO> newProducts) {
        logger.info("cart add products");


        for (CartDTO cartDTO : newProducts) {

            if (!cartDTO.Validate()) {
//                return ResponseEntity.badRequest().body(null);
                continue;
            }

            // где то должна создаваться или же если не найдена создаваться
            Optional<CartEntity> userCart = this.cartService.findEntityByUserId(cartDTO.getUserId());
            if (userCart.isEmpty()) {
//                return ResponseEntity.badRequest().body(null);
                continue;
            }
            Optional<ProductEntity> productInCart = this.productService.findEntityProductById(cartDTO.getProductId());
            if (productInCart.isEmpty()) {
//                return ResponseEntity.badRequest().body(null);
                continue;
            }

            logger.info("cart add product: {}", cartDTO.getProductId());
            this.cartService.addProductInCart(new ProductInCartEntity(userCart.get(), productInCart.get(), cartDTO.getCount()));
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/cart/get")
    public ResponseEntity<List<ProductList>> getProduct(@RequestParam UUID userId) {
        System.out.println("userId" + userId);
        List<ProductList> result = this.cartService.findByUserId(userId);
        return ResponseEntity.ok(result);
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

    // Работа с корзиной //

}
