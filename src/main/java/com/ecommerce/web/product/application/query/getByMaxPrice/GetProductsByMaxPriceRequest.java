package com.ecommerce.web.product.application.query.getByMaxPrice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsByMaxPriceRequest {
    private Double maxPrice;
}

