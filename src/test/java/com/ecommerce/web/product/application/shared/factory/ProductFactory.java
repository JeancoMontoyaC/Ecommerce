package com.ecommerce.web.product.application.shared.factory;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;

public class ProductFactory {

    public final Product product1;
    public final Product product2;
    public final Product product3;
    public final Product productUpdated;

    public final ProductDto productDto1;
    public final ProductDto productDto2;
    public final ProductDto productDto3;
    public final ProductDto productDtoUpdated;

    public ProductFactory() {
        this.product1 = Product.builder()
                .id(1L)
                .name("Laptop")
                .description("High performance laptop")
                .price(999.99)
                .category("Electronics")
                .available(true)
                .stock(10)
                .build();

        this.product2 = Product.builder()
                .id(2L)
                .name("Mouse")
                .description("Wireless mouse")
                .price(25.99)
                .category("Electronics")
                .available(true)
                .stock(50)
                .build();

        this.product3 = Product.builder()
                .id(3L)
                .name("Keyboard")
                .description("Mechanical keyboard")
                .price(149.99)
                .category("Electronics")
                .available(true)
                .stock(25)
                .build();

        this.productUpdated = Product.builder()
                .id(1L)
                .name("Laptop Updated")
                .description("High performance laptop")
                .price(899.99)
                .category("Electronics")
                .available(true)
                .stock(5)
                .build();

        this.productDto1 = ProductDto.builder()
                .id(1L)
                .name("Laptop")
                .description("High performance laptop")
                .price(999.99)
                .category("Electronics")
                .available(true)
                .stock(10)
                .build();

        this.productDto2 = ProductDto.builder()
                .id(2L)
                .name("Mouse")
                .description("Wireless mouse")
                .price(25.99)
                .category("Electronics")
                .available(true)
                .stock(50)
                .build();

        this.productDto3 = ProductDto.builder()
                .id(3L)
                .name("Keyboard")
                .description("Mechanical keyboard")
                .price(149.99)
                .category("Electronics")
                .available(true)
                .stock(25)
                .build();

        this.productDtoUpdated = ProductDto.builder()
                .id(1L)
                .name("Laptop Updated")
                .description("High performance laptop")
                .price(899.99)
                .category("Electronics")
                .available(true)
                .stock(5)
                .build();
    }
}


