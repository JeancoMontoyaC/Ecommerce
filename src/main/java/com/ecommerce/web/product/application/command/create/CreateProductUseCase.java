package com.ecommerce.web.product.application.command.create;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class CreateProductUseCase {
    private final ProductRepository productRepository;

    @Async
    public void execute(CreateProductRequest request) {
        log.info("Creating product with name: {}", request.getName());

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .available(request.getAvailable())
                .shortDescription(request.getShortDescription())
                .discountPercentage(request.getDiscountPercentage())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();

        productRepository.save(product);
        log.info("Product created successfully with name: {}", request.getName());
    }
}
