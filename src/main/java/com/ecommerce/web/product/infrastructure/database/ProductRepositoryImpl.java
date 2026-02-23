package com.ecommerce.web.product.infrastructure.database;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import com.ecommerce.web.product.infrastructure.persistence.SpringDataProductRepository;
import jakarta.transaction.Transactional;
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
                .available(product.getAvailable())
                .shortDescription(product.getShortDescription())
                .discountPercentage(product.getDiscountPercentage())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
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

    @Override
    public List<Product> findByMinPrice(Double minPrice) {
        List<ProductEntity> entities = springDataProductRepository.findByPriceGreaterThanEqual(minPrice);
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }

    @Override
    public List<Product> findByMaxPrice(Double maxPrice) {
        List<ProductEntity> entities = springDataProductRepository.findByPriceLessThanEqual(maxPrice);
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }

    @Override
    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        List<ProductEntity> entities = springDataProductRepository.findByPriceBetween(minPrice, maxPrice);
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }

    @Override
    public List<Product> findByCategory(String category) {
        List<ProductEntity> entities = springDataProductRepository.findByCategory(category);
        return entities.stream()
                .map(productMapper::mapToProduct)
                .toList();
    }

    @Override
    @Transactional
    public Product partialUpdate(Long id, Product productChanges) {
        ProductEntity product = springDataProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (productChanges.getName() != null) {
            product.setName(productChanges.getName());
        }

        if (productChanges.getDescription() != null) {
            product.setDescription(productChanges.getDescription());
        }

        if (productChanges.getShortDescription() != null) {
            product.setShortDescription(productChanges.getShortDescription());
        }

        if (productChanges.getPrice() != null) {
            product.setPrice(productChanges.getPrice());
        }

        if (productChanges.getDiscountPercentage() != null) {
            product.setDiscountPercentage(productChanges.getDiscountPercentage());
        }

        if (productChanges.getImageUrl() != null) {
            product.setImageUrl(productChanges.getImageUrl());
        }

        if (productChanges.getStock() != null) {
            product.setStock(productChanges.getStock());
        }

        if (productChanges.getAvailable() != null) {
            product.setAvailable(productChanges.getAvailable());
        }

        if (productChanges.getCategory() != null) {
            product.setCategory(productChanges.getCategory());
        }

        return productMapper.mapToProduct(product);

    }
}
