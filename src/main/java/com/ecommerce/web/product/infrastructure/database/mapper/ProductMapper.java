package com.ecommerce.web.product.infrastructure.database.mapper;

import com.ecommerce.web.product.application.command.create.CreateProductRequest;
import com.ecommerce.web.product.application.query.getById.GetProductByIdRequest;
import com.ecommerce.web.product.domain.entity.Product;
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
                .shortDescription(productDto.getShortDescription())
                .discountPrice(productDto.getDiscountPrice())
                .stock(productDto.getStock())
                .build();
    };

    public GetProductByIdRequest toGetProductByIdRequest(Long id){
        return GetProductByIdRequest.builder()
                .id(id)
                .build();
    };

    public ProductDto mapToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .isAvailable(product.isAvailable())
                .shortDescription(product.getShortDescription())
                .discountPrice(product.getDiscountPrice())
                .stock(product.getStock())
                .build();
    };
}
