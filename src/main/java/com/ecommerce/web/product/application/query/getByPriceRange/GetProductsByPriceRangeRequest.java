package com.ecommerce.web.product.application.query.getByPriceRange;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsByPriceRangeRequest {
    private Double minPrice;
    private Double maxPrice;
}

