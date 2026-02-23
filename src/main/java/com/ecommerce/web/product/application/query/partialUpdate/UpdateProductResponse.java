package com.ecommerce.web.product.application.query.partialUpdate;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateProductResponse {
    private Product product;
}
