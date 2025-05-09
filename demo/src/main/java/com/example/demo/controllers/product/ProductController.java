package com.example.demo.controllers.product;

import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.controllers.product.dto.ProductinOrderDTO;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Product register controlle",
        description = "API for add new category, get some or all category, etc"
)
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    protected ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiResponse(responseCode = "200", description = "All product in register")
    @GetMapping("/products/getCategories")
    public ResponseEntity<List<ProductinOrderDTO>> getCategories() {
        this.logger.info("Getting categories");

        return ResponseEntity.ok(this.productService.findAllProducts());
    }

    @ApiResponse(responseCode = "200", description = "List<DTO> to add new product category in system. " +
            "If something is wrong, check the product status on return object")
    @PostMapping("/product/addCategory")
    public ResponseEntity<List<ProductDTO>> addProductCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO to create new category product",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            )
            @RequestBody List<ProductDTO> productCategory
    ) {
        this.logger.info("Adding new category");

        for (ProductDTO productDTO : productCategory) {
            if (!productDTO.Validate()) {
                productDTO.setReturnStatus("Not a valid data");
                continue;
            }

            this.productService.saveNewProduct(new ProductEntity(
                    productDTO.getArticleInRegistry(), productDTO.getName(), productDTO.getCategory()
            ));
            productDTO.setReturnStatus("Success");
        }
        return ResponseEntity.ok(productCategory);
    }

}
