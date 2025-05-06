package com.example.demo.controllers.market;

import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.service.MarketService;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.productsInMarket.repository.ProductsInMarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class MarketController {
    private final Logger logger = LoggerFactory.getLogger(MarketController.class);

    private final MarketService marketService;
    private final ProductsInMarketRepository productsInMarketRepository;

    public MarketController(MarketService marketService, ProductsInMarketRepository productsInMarketRepository) {
        this.marketService = marketService;
        this.productsInMarketRepository = productsInMarketRepository;
    }

    @GetMapping("/markets")
    public ResponseEntity<List<GetMarket>> getMarkets() {
        this.logger.info("Getting markets");

        return ResponseEntity.ok(this.marketService.findAllMarkets());
    }

    @PostMapping("/market/create")
    public ResponseEntity<MarketEntity> addMarket(@RequestBody MarketDTO body) {
        this.logger.info("Adding market: {}", body);

        if (!body.Validate()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(this.marketService.createNewMarket(body));
    }

    // взаимодействия с товарами
    // товары в магазинах, полный список
    @GetMapping("/products")
    public ResponseEntity<List<FullProductListInfo>> getProducts() {
        this.logger.info("Getting products");

        return ResponseEntity.ok(this.marketService.findMarketsPrice());
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductsInMarketEntity>> getProducts(@RequestParam UUID productId) {
        this.logger.info("Getting productsInMarketRepository");
        return ResponseEntity.ok(this.productsInMarketRepository.findByProductId(productId));
    }


    @PostMapping("/product/add")
    public ResponseEntity<List<UpdateProductDTO>> addProduct(@RequestBody List<UpdateProductDTO> body) {
        this.logger.info("Adding product: {}", body);

        for (UpdateProductDTO product : body) {
            if (!product.Validate()) {
                product.setReturnStatus("Ошибка ввода");
                continue;
            }

            marketService.addProducts(product);
        }
        return ResponseEntity.ok(body);
    }
}