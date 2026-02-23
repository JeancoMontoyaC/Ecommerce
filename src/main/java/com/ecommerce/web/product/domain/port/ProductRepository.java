package com.ecommerce.web.product.domain.port;

import com.ecommerce.web.product.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByName(String name);
    List<Product> findByMinPrice(Double minPrice);
    Product partialUpdate(Long id, Product productChanges);
}
