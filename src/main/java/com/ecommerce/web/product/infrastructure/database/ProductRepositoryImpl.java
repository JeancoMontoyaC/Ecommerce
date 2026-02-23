package com.ecommerce.web.product.infrastructure.database;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;
import com.ecommerce.web.product.infrastructure.persistence.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
                .shortDescription(product.getShortDescription())
                .discountPrice(product.getDiscountPrice())
                .stock(product.getStock())
                .build();

        springDataProductRepository.save(entity);
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<ProductEntity> productEntity = springDataProductRepository.findById(id);

        return productEntity.map(entity -> Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .shortDescription(entity.getShortDescription())
                .price(entity.getPrice())
                .discountPrice(entity.getDiscountPrice())
                .imageUrl(entity.getImageUrl())
                .isAvailable(entity.isAvailable())
                .stock(entity.getStock())
                .build());
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = springDataProductRepository.findAll();
        return entities.stream().map(entity -> Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .shortDescription(entity.getShortDescription())
                .price(entity.getPrice())
                .discountPrice(entity.getDiscountPrice())
                .imageUrl(entity.getImageUrl())
                .isAvailable(entity.isAvailable())
                .stock(entity.getStock())
                .build()).toList();
    }
}
