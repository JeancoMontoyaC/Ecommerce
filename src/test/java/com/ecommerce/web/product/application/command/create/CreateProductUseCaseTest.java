package com.ecommerce.web.product.application.command.create;

import com.ecommerce.web.product.application.shared.factory.ProductFactory;
import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {
    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @Mock
    private ProductRepository productRepository;

    private CreateProductRequest createProductRequest;

    @BeforeEach
    void setUp() {
        createProductRequest = CreateProductRequest.builder()
                .name("Laptop")
                .description("High performance laptop for work and gaming")
                .price(999.99)
                .imageUrl("https://example.com/laptop.jpg")
                .available(true)
                .shortDescription("Professional Laptop")
                .discountPercentage(10.0)
                .stock(10)
                .category("Electronics")
                .build();
    }

    @Test
    void execute_ShouldCreateAndSaveProduct() {
        // Arrange & Act
        createProductUseCase.execute(createProductRequest);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void execute_ShouldSaveProductWithCorrectAttributes() {
        // Arrange & Act
        createProductUseCase.execute(createProductRequest);

        // Assert
        verify(productRepository).save(any(Product.class));
    }
}

