package com.ecommerce.web.product.application.query.partialUpdate;

import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UpdateProductUseCase {
    private final ProductRepository productRepository;

    public UpdateProductResponse execute(UpdateProductRequest request) {
        var product = productRepository.partialUpdate(
                request.getId(), request.getProduct()
        );

        return new UpdateProductResponse(product);
    }
}
