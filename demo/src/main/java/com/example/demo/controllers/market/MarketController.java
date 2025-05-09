package com.example.demo.controllers.market;

import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.service.MarketService;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.productsInMarket.repository.ProductsInMarketRepository;
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
        name = "Market controller",
        description = "API for create, manipulation with market and products in the market"
)
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

    @ApiResponse(responseCode = "200", description = "All markets in system")
    @GetMapping("/markets")
    public ResponseEntity<List<GetMarket>> getMarkets() {
        this.logger.info("Getting markets");

        return ResponseEntity.ok(this.marketService.findAllMarkets());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All markets in system"),
            @ApiResponse(responseCode = "400", description = "Invalid field in marketDTO")
    })
    @PostMapping("/market/create")
    public ResponseEntity<MarketEntity> addMarket(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "DTO to create new market in system",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarketDTO.class)
                    )
            )
            @RequestBody MarketDTO body
    ) {
        this.logger.info("Adding market: {}", body);

        if (!body.Validate()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Invalid market");
        }

        return ResponseEntity.ok(this.marketService.createNewMarket(body));
    }

    @ApiResponse(responseCode = "200", description = "All products that are in markets")
    @GetMapping("/products")
    public ResponseEntity<List<FullProductListInfo>> getProducts() {
        this.logger.info("Getting products");

        return ResponseEntity.ok(this.marketService.findMarketsPrice());
    }

    @ApiResponse(responseCode = "200", description = "Find the product by id")
    @GetMapping("/product")
    public ResponseEntity<List<ProductsInMarketEntity>> getProducts(
            @Parameter(description = "UUID product", example = "a8ca68d2-3b30-4c1e-b74a-a9cc14419bd8")
            @RequestParam UUID productId
    ) {
        this.logger.info("Getting productsInMarketRepository");
        return ResponseEntity.ok(this.productsInMarketRepository.findByProductId(productId));
    }


    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Products add or update product in market")
    )
    @PostMapping("/product/add")
    public ResponseEntity<List<UpdateProductDTO>> addProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List<DTO> to add or update product in market. " +
                            "If something wrong, check the product status on return object",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateProductDTO.class)
                    )
            )
            @RequestBody List<UpdateProductDTO> body
    ) {
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