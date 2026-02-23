package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.application.command.create.CreateProductUseCase;
import com.ecommerce.web.product.application.query.getAll.GetAllProductsUseCase;
import com.ecommerce.web.product.application.query.getById.GetProductByIdUseCase;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameUseCase;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductsByNameUseCase getProductsByNameUseCase;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(
             @RequestBody @Valid ProductDto productDto
    ) {
        var request = productMapper.toCreateProductRequest(productDto);
        createProductUseCase.execute(request);

        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        var productRequest = productMapper.toGetProductByIdRequest(id);
        var productResponse = getProductByIdUseCase.execute(productRequest);

        return productResponse.getProduct() != null
                ? ResponseEntity.ok(productMapper.mapToProductDto(productResponse.getProduct()))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var products = getAllProductsUseCase.execute().getProducts();
        var productDtos = products.stream()
                .map(productMapper::mapToProductDto)
                .toList();

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        var request = productMapper.toGetProductsByNameRequest(name);
        var response = getProductsByNameUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        return ResponseEntity.ok(productDtos);
    }
}
