package com.ecommerce.web.product.application.query.getByName;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class GetProductsByNameResponse {
    private List<Product> products;

}
