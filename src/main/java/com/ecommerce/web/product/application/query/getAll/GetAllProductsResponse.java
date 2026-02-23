package com.ecommerce.web.product.application.query.getAll;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class GetAllProductsResponse {
    private List<Product> products;
}
