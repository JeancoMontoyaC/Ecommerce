package com.ecommerce.web.product.application.query.getByMinPrice;

import com.ecommerce.web.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetProductsByMinPriceResponse {
    private List<Product> products;
}

