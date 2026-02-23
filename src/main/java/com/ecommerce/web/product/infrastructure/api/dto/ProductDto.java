package com.ecommerce.web.product.infrastructure.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @Length(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
    private String name;
    @Length(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;
    @DecimalMin(value = "0.01", inclusive = false)
    @DecimalMax(value = "999.99", inclusive = false)
    private Double price;
    private String imageUrl;
    private Boolean available;
    private String shortDescription;
    private Double discountPrice;
    private Integer stock;
}
