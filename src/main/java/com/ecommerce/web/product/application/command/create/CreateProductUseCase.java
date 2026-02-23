package com.ecommerce.web.product.application.command.create;

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
                .available(request.getAvailable())
                .shortDescription(request.getShortDescription())
                .discountPercentage(request.getDiscountPercentage())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();

        productRepository.save(product);
    }
}
