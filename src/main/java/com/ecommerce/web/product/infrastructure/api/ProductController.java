package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.application.command.CreateProduct.CreateProductUseCase;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final CreateProductUseCase useCase;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<Void> createProduct(
             @RequestBody @Valid ProductDto productDto
    ) {
       useCase.execute(
                productMapper.toCreateProductRequest(productDto)
       );

        return ResponseEntity.ok().build();
    }
}
