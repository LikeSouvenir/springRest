package com.example.demo.controllers.users.backet;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.users.backet.dto.backetDTO;
import com.example.demo.core.markets.product.service.ProductService;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.users.backet.entity.backetEntity;
import com.example.demo.core.users.backet.service.backetService;
import com.example.demo.core.users.profile.entity.ProfileEntity;
import com.example.demo.core.users.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class backetController {

    private final Logger logger = LoggerFactory.getLogger(backetController.class);

    private final backetService backetService;
    private final UserService userService;
    private final ProductService productService;

    public backetController(backetService backetService, UserService userService, ProductService productService) {
        this.backetService = backetService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/backet/add") // не указывается кол-во
    public ResponseEntity<backetEntity> addProduct(@RequestBody backetDTO backet) {
        logger.info("backet add product");

        if (!backet.Validate()) {
            System.out.println("Ошибка валидации");
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("Validate пройден");

        Optional<ProfileEntity> profile = this.userService.findById(backet.getProfile());
        if (profile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("profile пройден");

        Optional<ProductsInMarketEntity> products_in_market = this.productService.findById(backet.getProducts_in_market());
        if (products_in_market.isEmpty()) {
            System.out.println("Ошибка products_in_market.isEmpty()");
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("products_in_market пройден");


        List<ProductsInMarketEntity> pim = new ArrayList<>();
        pim.add(products_in_market.get());
        System.out.println("new ArrayList<>(); пройден");

        this.backetService.addTobacket(new backetEntity(profile.get(), pim));

        System.out.println("savebacketEntity пройден");
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/backet/get") // profileId
    public ResponseEntity<List<ProductList>> getProduct(@RequestParam UUID profileId) {
        List<ProductList> result = this.backetService.findByProfileId(profileId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/backet/getAll")
    public ResponseEntity<List<ProductList>> getAll() {
        return ResponseEntity.ok(this.backetService.findAllProducts());
    }

    @DeleteMapping("/backet/delete") // profile
    public ResponseEntity<?> deleteProduct(@RequestParam UUID profile) {
        backetService.removeFrombacket(profile);
        return ResponseEntity.ok().body(null);
    }
}
