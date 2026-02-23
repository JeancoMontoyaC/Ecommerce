package com.ecommerce.web.product.application.query.getById;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductByIdRequest {
    private Long id;
}
