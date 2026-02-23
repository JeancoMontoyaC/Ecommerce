package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.application.command.create.CreateProductUseCase;
import com.ecommerce.web.product.application.query.getAll.GetAllProductsUseCase;
import com.ecommerce.web.product.application.query.getById.GetProductByIdUseCase;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameUseCase;
import com.ecommerce.web.product.application.query.getByMinPrice.GetProductsByMinPriceUseCase;
import com.ecommerce.web.product.application.query.getByMaxPrice.GetProductsByMaxPriceUseCase;
import com.ecommerce.web.product.application.query.getByPriceRange.GetProductsByPriceRangeUseCase;
import com.ecommerce.web.product.application.query.partialUpdate.UpdateProductUseCase;
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
    private final GetProductsByMinPriceUseCase getProductsByMinPriceUseCase;
    private final GetProductsByMaxPriceUseCase getProductsByMaxPriceUseCase;
    private final GetProductsByPriceRangeUseCase getProductsByPriceRangeUseCase;
    private final UpdateProductUseCase updateProductUseCase;
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

    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> partialUpdate(
            @PathVariable Long id,
            @RequestBody ProductDto product) {
        var request = productMapper.toUpdateProductRequest(id, product);
        var response = updateProductUseCase.execute(request);
        var updatedProduct = productMapper.mapToProductDto(response.getProduct());
        return ResponseEntity.ok(updatedProduct);
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

    @GetMapping("/filter/min-price/{minPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByMinPrice(@PathVariable Double minPrice) {
        var request = productMapper.toGetProductsByMinPriceRequest(minPrice);
        var response = getProductsByMinPriceUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/max-price/{maxPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByMaxPrice(@PathVariable Double maxPrice) {
        var request = productMapper.toGetProductsByMaxPriceRequest(maxPrice);
        var response = getProductsByMaxPriceUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        var request = productMapper.toGetProductsByPriceRangeRequest(minPrice, maxPrice);
        var response = getProductsByPriceRangeUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        return ResponseEntity.ok(productDtos);
    }
}
