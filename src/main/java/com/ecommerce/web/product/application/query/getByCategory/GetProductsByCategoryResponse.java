package com.ecommerce.web.product.application.query.getByCategory;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetProductsByCategoryResponse {
    private List<Product> products;
}

