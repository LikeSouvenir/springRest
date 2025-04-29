package com.example.demo.controllers.users.backed;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.users.backed.dto.BackedDTO;
import com.example.demo.core.markets.product.service.ProductService;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.users.backed.entity.BackedEntity;
import com.example.demo.core.users.backed.service.BackedService;
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
public class BackedController {

    private final Logger logger = LoggerFactory.getLogger(BackedController.class);

    private final BackedService backedService;
    private final UserService userService;
    private final ProductService productService;

    public BackedController(BackedService backedService, UserService userService, ProductService productService) {
        this.backedService = backedService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/backed/add") // не указывается кол-во
    public ResponseEntity<BackedEntity> addProduct(@RequestBody BackedDTO backed) {
        logger.info("backed add product");

        if (!backed.Validate()) {
            System.out.println("Ошибка валидации");
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("Validate пройден");

        Optional<ProfileEntity> profile = this.userService.findById(backed.getProfile());
        if (profile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("profile пройден");

        Optional<ProductsInMarketEntity> products_in_market = this.productService.findById(backed.getProducts_in_market());
        if (products_in_market.isEmpty()) {
            System.out.println("Ошибка products_in_market.isEmpty()");
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("products_in_market пройден");


        List<ProductsInMarketEntity> pim = new ArrayList<>();
        pim.add(products_in_market.get());
        System.out.println("new ArrayList<>(); пройден");

        this.backedService.addToBacked(new BackedEntity(profile.get(), pim));

        System.out.println("saveBackedEntity пройден");
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/backed/get") // profileId
    public ResponseEntity<List<ProductList>> getProduct(@RequestParam UUID profileId) {
        List<ProductList> result = this.backedService.findByProfileId(profileId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/backed/getAll")
    public ResponseEntity<List<ProductList>> getAll() {
        return ResponseEntity.ok(this.backedService.findAllProducts());
    }

    @DeleteMapping("/backed/delete") // profile
    public ResponseEntity<?> deleteProduct(@RequestParam UUID profile) {
        backedService.removeFromBacked(profile);
        return ResponseEntity.ok().body(null);
    }
}
