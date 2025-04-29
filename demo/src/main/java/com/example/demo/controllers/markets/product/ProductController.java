package com.example.demo.controllers.markets.product;

import com.example.demo.controllers.markets.product.dto.ProductDTO;
import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.markets.product.dto.UpdateProductDTO;
import com.example.demo.core.markets.market.entity.MarketEntity;
import com.example.demo.core.markets.market.service.MarketService;
import com.example.demo.core.markets.product.entity.ProductEntity;
import com.example.demo.core.markets.product.service.ProductService;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ProductList>> getProducts() {
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
            MarketEntity marketEntity = this.marketService.findById(product.getMarketId());
            if (marketEntity == null) {
                product.setReturnStatus("Магазин не найден");
                continue;
            }
            System.out.println("\nAdding product: " + product.getArticleInRegister());

            ProductEntity productEntity = this.productService.findProductByArticle(product.getArticleInRegister());
            // проверка на наличие артикля в системе
            if (productEntity == null) {
                product.setReturnStatus("Артикль не найден");
                continue;
            }
            System.out.println("productEntity = " + productEntity.getName());

            // проверяем существует ли подобная позиция в магазине
            ProductsInMarketEntity productsInMarketEntity = this.productService.findProductInMarketById(
                    marketEntity.getId(), productEntity.getId()
            );
            System.out.println("productsInMarketEntity прошел");
            // если - нет создаем
            if (productsInMarketEntity == null) {
                System.out.println("productsInMarketEntity не найден");

                this.productService.addProductInMarket(new ProductsInMarketEntity(
                        product.getCount(), product.getCost(), marketEntity, productEntity)
                );

                product.setReturnStatus("Товар добавлен");
                System.out.println("Категория создана");
            } else { // если да, обновляем
                System.out.println("productsInMarketEntity найден");

                this.productService.updateProductInMarket(productsInMarketEntity.getId(), product);

                System.out.println("Категория обновлена");
                product.setReturnStatus("Товар обновлен");
            }
        }

        return ResponseEntity.ok(body);
    }
}
