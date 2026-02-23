package com.ecommerce.web.product.application.command.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {
    private String name;
    private String description;
    private String shortDescription;
    private Double price;
    private Double discountPercentage;
    private String imageUrl;
    private Boolean available;
    private Integer stock;
}