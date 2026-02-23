package com.ecommerce.web.product.application.query.getByCategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsByCategoryRequest {
    private String category;
}

