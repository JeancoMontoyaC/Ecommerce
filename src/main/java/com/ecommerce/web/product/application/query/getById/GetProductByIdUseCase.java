package com.ecommerce.web.product.application.query.getById;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
@Slf4j
public class GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdResponse execute(GetProductByIdRequest request) {
        log.info("Fetching product with id: {}", request.getId());

        Optional<Product> product = productRepository.findById(request.getId());

        if (product.isPresent()) {
            log.info("Product found with id: {}", request.getId());
        } else {
            log.warn("Product not found with id: {}", request.getId());
        }

        return new GetProductByIdResponse(product);
    }

}
