package com.ecommerce.web.product.application.query.getByMaxPrice;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductsByMaxPriceUseCase {
    private final ProductRepository productRepository;

    public GetProductsByMaxPriceResponse execute(GetProductsByMaxPriceRequest request) {
        log.info("Fetching products with maximum price: {}", request.getMaxPrice());

        List<Product> products = productRepository.findByMaxPrice(request.getMaxPrice());

        log.info("Retrieved {} products with maximum price: {}", products.size(), request.getMaxPrice());

        return new GetProductsByMaxPriceResponse(products);
    }
}

