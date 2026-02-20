package com.ecommerce.web.product.domain.port;

import com.ecommerce.web.product.domain.entity.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
}
