package com.ecommerce.web.product.application.query.getByCategory;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductsByCategoryUseCase {
    private final ProductRepository productRepository;

    public GetProductsByCategoryResponse execute(GetProductsByCategoryRequest request) {
        log.info("Fetching products by category: {}", request.getCategory());

        List<Product> products = productRepository.findByCategory(request.getCategory());

        log.info("Retrieved {} products with category: {}", products.size(), request.getCategory());

        return new GetProductsByCategoryResponse(products);
    }
}

