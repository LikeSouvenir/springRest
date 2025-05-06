package com.example.demo.controllers.product;

import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    protected ProductController(ProductService productService) {
        this.productService = productService;
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

}
