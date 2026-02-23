package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductApi {
    public ResponseEntity<Void> createProduct(ProductDto productDto);
    public ResponseEntity<ProductDto> getProductById(Long id);
    public ResponseEntity<List<ProductDto>> getAllProducts();
    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto);
    public ResponseEntity<List<ProductDto>> getProductsByName(String name);
    public ResponseEntity<List<ProductDto>> getProductsByMinPrice(Double minPrice);
    public ResponseEntity<List<ProductDto>> getProductsByMaxPrice(Double maxPrice);
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(Double minPrice, Double maxPrice);
    public ResponseEntity<List<ProductDto>> getProductsByCategory(String category);
}
