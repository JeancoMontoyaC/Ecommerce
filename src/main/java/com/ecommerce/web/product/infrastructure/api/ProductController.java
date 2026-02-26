package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.application.command.create.CreateProductUseCase;
import com.ecommerce.web.product.application.query.getAll.GetAllProductsUseCase;
import com.ecommerce.web.product.application.query.getById.GetProductByIdUseCase;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameUseCase;
import com.ecommerce.web.product.application.query.getByMinPrice.GetProductsByMinPriceUseCase;
import com.ecommerce.web.product.application.query.getByMaxPrice.GetProductsByMaxPriceUseCase;
import com.ecommerce.web.product.application.query.getByPriceRange.GetProductsByPriceRangeUseCase;
import com.ecommerce.web.product.application.query.getByCategory.GetProductsByCategoryUseCase;
import com.ecommerce.web.product.application.query.partialUpdate.UpdateProductUseCase;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Slf4j
public class ProductController implements ProductApi {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductsByNameUseCase getProductsByNameUseCase;
    private final GetProductsByMinPriceUseCase getProductsByMinPriceUseCase;
    private final GetProductsByMaxPriceUseCase getProductsByMaxPriceUseCase;
    private final GetProductsByPriceRangeUseCase getProductsByPriceRangeUseCase;
    private final GetProductsByCategoryUseCase getProductsByCategoryUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(
             @RequestBody @Valid ProductDto productDto
    ) {
        log.info("Creating product with name: {}", productDto.getName());

        var request = productMapper.toCreateProductRequest(productDto);
        createProductUseCase.execute(request);

        log.info("Created product with id: {}", request.getName());
        return  ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);

        var productRequest = productMapper.toGetProductByIdRequest(id);
        var productResponse = getProductByIdUseCase.execute(productRequest);

        if (productResponse.getProduct() != null) {
            log.info("Product found with id: {}", id);
            return ResponseEntity.ok(productMapper.mapToProductDto(productResponse.getProduct()));
        } else {
            log.warn("Product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Fetching all products");

        var products = getAllProductsUseCase.execute().getProducts();
        var productDtos = products.stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Retrieved {} products", productDtos.size());
        return ResponseEntity.ok(productDtos);
    }

    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> partialUpdate(
            @PathVariable Long id,
            @RequestBody ProductDto product) {
        log.info("Updating product with id: {}", id);

        var request = productMapper.toUpdateProductRequest(id, product);
        var response = updateProductUseCase.execute(request);
        var updatedProduct = productMapper.mapToProductDto(response.getProduct());

        log.info("Product updated with id: {}", id);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        log.info("Searching products by name: {}", name);

        var request = productMapper.toGetProductsByNameRequest(name);
        var response = getProductsByNameUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Found {} products with name: {}", productDtos.size(), name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/min-price/{minPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByMinPrice(@PathVariable Double minPrice) {
        log.info("Fetching products with minimum price: {}", minPrice);

        var request = productMapper.toGetProductsByMinPriceRequest(minPrice);
        var response = getProductsByMinPriceUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Retrieved {} products with minimum price: {}", productDtos.size(), minPrice);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/max-price/{maxPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByMaxPrice(@PathVariable Double maxPrice) {
        log.info("Fetching products with maximum price: {}", maxPrice);

        var request = productMapper.toGetProductsByMaxPriceRequest(maxPrice);
        var response = getProductsByMaxPriceUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Retrieved {} products with maximum price: {}", productDtos.size(), maxPrice);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        log.info("Fetching products with price range: {} - {}", minPrice, maxPrice);

        var request = productMapper.toGetProductsByPriceRangeRequest(minPrice, maxPrice);
        var response = getProductsByPriceRangeUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Retrieved {} products with price range: {} - {}", productDtos.size(), minPrice, maxPrice);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        log.info("Fetching products by category: {}", category);

        var request = productMapper.toGetProductsByCategoryRequest(category);
        var response = getProductsByCategoryUseCase.execute(request);
        var productDtos = response.getProducts().stream()
                .map(productMapper::mapToProductDto)
                .toList();

        log.info("Retrieved {} products with category: {}", productDtos.size(), category);
        return ResponseEntity.ok(productDtos);
    }
}
