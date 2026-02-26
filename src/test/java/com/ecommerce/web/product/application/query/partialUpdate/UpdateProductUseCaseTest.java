package com.ecommerce.web.product.application.query.partialUpdate;

import com.ecommerce.web.product.application.shared.factory.ProductFactory;
import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {
    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private UpdateProductRequest updateProductRequest;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        updateProductRequest = new UpdateProductRequest(1L, factory.product1);
    }

    @Test
    void execute_ShouldUpdateProductSuccessfully() {
        // Arrange
        when(productRepository.partialUpdate(anyLong(), any(Product.class))).thenReturn(factory.productUpdated);

        // Act
        UpdateProductResponse response = updateProductUseCase.execute(updateProductRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProduct());
        assertEquals("Laptop Updated", response.getProduct().getName());
    }

    @Test
    void execute_ShouldReturnUpdatedProductWithCorrectData() {
        // Arrange
        when(productRepository.partialUpdate(anyLong(), any(Product.class))).thenReturn(factory.productUpdated);

        // Act
        UpdateProductResponse response = updateProductUseCase.execute(updateProductRequest);

        // Assert
        assertEquals(1L, response.getProduct().getId());
        assertEquals("Laptop Updated", response.getProduct().getName());
        assertEquals(899.99, response.getProduct().getPrice());
        assertEquals(5, response.getProduct().getStock());
    }

    @Test
    void execute_ShouldReturnProductWithUpdatedPrice() {
        // Arrange
        when(productRepository.partialUpdate(anyLong(), any(Product.class))).thenReturn(factory.productUpdated);

        // Act
        UpdateProductResponse response = updateProductUseCase.execute(updateProductRequest);

        // Assert
        assertEquals(899.99, response.getProduct().getPrice());
        assertTrue(response.getProduct().getAvailable());
    }
}

