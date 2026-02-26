package com.ecommerce.web.product.application.query.getByMinPrice;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductsByMinPriceUseCase {
    private final ProductRepository productRepository;

    public GetProductsByMinPriceResponse execute(GetProductsByMinPriceRequest request) {
        log.info("Fetching products with minimum price: {}", request.getMinPrice());

        List<Product> products = productRepository.findByMinPrice(request.getMinPrice());

        log.info("Retrieved {} products with minimum price: {}", products.size(), request.getMinPrice());

        return new GetProductsByMinPriceResponse(products);
    }
}

