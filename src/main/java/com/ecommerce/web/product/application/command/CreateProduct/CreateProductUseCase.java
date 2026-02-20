package com.ecommerce.web.product.application.command.CreateProduct;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public void execute(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .isAvailable(request.isAvailable())
                .build();

        productRepository.save(product);
    }
}
