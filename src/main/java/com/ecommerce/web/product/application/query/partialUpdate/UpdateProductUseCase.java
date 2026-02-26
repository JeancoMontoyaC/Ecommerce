package com.ecommerce.web.product.application.query.partialUpdate;

import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class UpdateProductUseCase {
    private final ProductRepository productRepository;

    public UpdateProductResponse execute(UpdateProductRequest request) {
        log.info("Updating product with id: {}", request.getId());

        var product = productRepository.partialUpdate(
                request.getId(), request.getProduct()
        );

        log.info("Product updated successfully with id: {}", request.getId());

        return new UpdateProductResponse(product);
    }
}
