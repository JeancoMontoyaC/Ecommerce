package com.ecommerce.web.product.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column
    private String shortDescription;

    @Column(nullable = false)
    private Double price;

    @Column
    private Double discountPrice;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Boolean available;

    @Column
    private Integer stock;
}
