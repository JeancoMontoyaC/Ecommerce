package com.ecommerce.web.product.infrastructure.persistence;

import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
}
