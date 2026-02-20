package com.ecommerce.web.product.infrastructure.database.mapper;

import com.ecommerce.web.product.application.command.CreateProduct.CreateProductRequest;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;

import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    public CreateProductRequest toCreateProductRequest(ProductDto productDto){
        return CreateProductRequest.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .isAvailable(productDto.isAvailable())
                .build(
        );
    };
}
