package com.ecommerce.web.product.application.query.getByMinPrice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsByMinPriceRequest {
    private Double minPrice;
}

