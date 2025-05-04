package com.example.demo.controllers.product;

import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.service.MarketService;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.service.ProductService;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final MarketService marketService;

    protected ProductController(ProductService productService, MarketService marketService) {
        this.productService = productService;
        this.marketService = marketService;
    }

    // категории
    @GetMapping("/products/getCategories")
    public ResponseEntity<List<ProductDTO>> getCategories() {
        this.logger.info("Getting categories");

        return ResponseEntity.ok(this.productService.findAllProducts());
    }

    @PostMapping("/product/addCategory")
    public ResponseEntity<List<ProductDTO>> addProductCategory(@RequestBody List<ProductDTO> productCategory) {
        this.logger.info("Adding new category");

        for (ProductDTO productDTO : productCategory) {
            if (!productDTO.Validate()) {
                // прикреплять статус добавления или удалять?
                continue;
            }
            this.productService.saveNewProduct(new ProductEntity(
                    productDTO.getArticleInRegistry(), productDTO.getName(), productDTO.getCategory()
            ));
        }
        return ResponseEntity.ok(productCategory);
    }

    // товары в магазине
    @GetMapping("/products")
    public ResponseEntity<List<FullProductListInfo>> getProducts() {
        this.logger.info("Getting products");

        return ResponseEntity.ok(this.productService.findMarketsPrice());
    }

    @PostMapping("/product/add")
    public ResponseEntity<List<UpdateProductDTO>> addProduct(@RequestBody List<UpdateProductDTO> body) {
        this.logger.info("Adding product: {}", body);

        for (UpdateProductDTO product : body) {
            if (!product.Validate()) {
                product.setReturnStatus("Ошибка ввода");
                continue;
            }

            // проверка на наличие магазина
            Optional<MarketEntity> marketEntity = this.marketService.findById(product.getMarketId());
            if (marketEntity.isEmpty()) {
                continue;
            }
            System.out.println("\nAdding product: " + product.getArticleInRegister());

            Optional<ProductEntity> productEntity = this.productService.findEntityProductByArticle(product.getArticleInRegister());
            // проверка на наличие артикля в системе
            if (productEntity.isEmpty()) {
                continue;
            }

            // проверяем существует ли подобная позиция в магазине
            Optional<ProductsInMarketEntity> productsInMarketEntity = this.productService.findProductInMarketById(
                    marketEntity.get().getId(), productEntity.get().getId()
            );
            // если - нет создаем
            if (productsInMarketEntity.isEmpty()) {
                System.out.println("productsInMarketEntity не найден");

                this.productService.addProductInMarket(new ProductsInMarketEntity(
                        product.getCount(), product.getCost(), marketEntity.get(), productEntity.get())
                );

                product.setReturnStatus("Товар добавлен");
                System.out.println("Категория создана");
            } else { // если да, обновляем
                System.out.println("productsInMarketEntity найден");

                this.productService.updateProductInMarket(productsInMarketEntity.get().getId(), product.getCount(), product.getCost());

                System.out.println("Категория обновлена");
                product.setReturnStatus("Товар обновлен");
            }
        }

        return ResponseEntity.ok(body);
    }
}
