package com.ecommerce.web.product.application.query.partialUpdate;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductRequest {
    private Long id;
    private Product product;
}
