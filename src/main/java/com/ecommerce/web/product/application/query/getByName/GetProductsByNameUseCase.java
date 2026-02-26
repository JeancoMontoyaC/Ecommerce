package com.ecommerce.web.product.application.query.getByName;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductsByNameUseCase {
    private final ProductRepository productRepository;

    public GetProductsByNameResponse execute(GetProductsByNameRequest request) {
        log.info("Searching products by name: {}", request.getName());

        List<Product> products = productRepository.findByName(request.getName());

        log.info("Found {} products with name: {}", products.size(), request.getName());

        return new GetProductsByNameResponse(products);
    }
}
