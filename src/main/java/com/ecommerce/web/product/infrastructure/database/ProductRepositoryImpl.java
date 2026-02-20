package com.ecommerce.web.product.infrastructure.database;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;
import com.ecommerce.web.product.infrastructure.persistence.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    @Override
    public void save(Product product) {

        ProductEntity entity = ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isAvailable(product.isAvailable())
                .build();

        springDataProductRepository.save(entity);
    }
}
