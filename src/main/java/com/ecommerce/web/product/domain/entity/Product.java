package com.ecommerce.web.product.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private Double price;
    private Double discountPrice;
    private String imageUrl;
    private Boolean available;
    private Integer stock;
}
