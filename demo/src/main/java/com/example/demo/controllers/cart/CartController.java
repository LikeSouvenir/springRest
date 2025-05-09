package com.example.demo.controllers.cart;

import com.example.demo.controllers.product.dto.ProductList;
import com.example.demo.controllers.cart.dto.InputCartDTO;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.service.CartService;
import com.example.demo.utils.exceptions.ValidationException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Cart controller",
        description = "API for manipulation with a cart"
)
@RestController
@RequestMapping("/api/v1")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products add to user cart"),
            @ApiResponse(responseCode = "400", description = "List is empty")
    })
    @PostMapping("/cart/addProducts")
    public ResponseEntity<List<InputCartDTO>> addProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List<DTO> to add or update product to user cart. " +
                            "If something wrong, check the product status on return object",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InputCartDTO.class)
                    )
            )
            @RequestBody List<InputCartDTO> newProducts
    ) {
        logger.info("cart add products");

        if (newProducts.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Empty products list");
        }
        cartService.addProductInCart(newProducts);

        return ResponseEntity.ok(newProducts);
    }

    @ApiResponse(responseCode = "200", description = "All products that are in the user cart")
    @GetMapping("/cart/get")
    public ResponseEntity<List<ProductList>> getProduct(
            @Parameter(description = "UUID user", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID userId
    ) {
        logger.info("cart get products");
        return ResponseEntity.ok(this.cartService.findListByUserId(userId));
    }

    @ApiResponse(responseCode = "200", description = "All products that are in all cart")
    @GetMapping("/cart/getAll")
    public ResponseEntity<List<ProductList>> getAll() {
        logger.info("get all carts");
        return ResponseEntity.ok(this.cartService.findAllProducts());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Drop products from user cart"),
            @ApiResponse(responseCode = "404", description = "User cart not found")
    })
    @DeleteMapping("/cart/delete")
    public ResponseEntity<?> deleteProduct(
            @Parameter(description = "UUID user", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID userId
    ) {
        logger.info("cart delete product");
        cartService.removeFromCart(userId);
        return ResponseEntity.ok(true);
    }
}
