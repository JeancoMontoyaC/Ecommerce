package com.ecommerce.web.product.application.command.CreateProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean isAvailable;
}