package com.ecommerce.web.product.infrastructure.database;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import com.ecommerce.web.product.infrastructure.persistence.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;
    private final ProductMapper productMapper;

    @Override
    public void save(Product product) {

        ProductEntity entity = ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isAvailable(product.isAvailable())
                .shortDescription(product.getShortDescription())
                .discountPrice(product.getDiscountPrice())
                .stock(product.getStock())
                .build();

        springDataProductRepository.save(entity);
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<ProductEntity> productEntity = springDataProductRepository.findById(id);

        return productEntity.map(productMapper::mapToProduct);
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = springDataProductRepository.findAll();
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }

    @Override
    public List<Product> findByName(String name) {
        List<ProductEntity> entities = springDataProductRepository.findByNameContaining(name);
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }
}
