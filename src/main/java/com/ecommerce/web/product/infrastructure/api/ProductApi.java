package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductApi {
    public ResponseEntity<Void> createProduct(ProductDto productDto);
}
