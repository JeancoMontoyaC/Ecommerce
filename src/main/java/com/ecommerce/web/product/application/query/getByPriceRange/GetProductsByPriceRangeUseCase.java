package com.ecommerce.web.product.application.query.getByPriceRange;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductsByPriceRangeUseCase {
    private final ProductRepository productRepository;

    public GetProductsByPriceRangeResponse execute(GetProductsByPriceRangeRequest request) {
        log.info("Fetching products with price range: {} - {}", request.getMinPrice(), request.getMaxPrice());

        List<Product> products = productRepository.findByPriceRange(request.getMinPrice(), request.getMaxPrice());

        log.info("Retrieved {} products with price range: {} - {}", products.size(), request.getMinPrice(), request.getMaxPrice());

        return new GetProductsByPriceRangeResponse(products);
    }
}

