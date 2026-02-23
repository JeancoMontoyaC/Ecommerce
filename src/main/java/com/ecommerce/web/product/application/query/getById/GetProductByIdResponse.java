package com.ecommerce.web.product.application.query.getById;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class GetProductByIdResponse {
    private Product product;

    public GetProductByIdResponse(Optional<Product> product) {
        this.product = product.orElse(null);
    }
}
