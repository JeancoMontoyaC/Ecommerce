package com.ecommerce.web.product.application.query.getByName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsByNameRequest {
    private String name;
}
