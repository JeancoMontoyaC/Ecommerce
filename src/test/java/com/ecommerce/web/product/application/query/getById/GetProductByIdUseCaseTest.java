package com.ecommerce.web.product.application.query.getById;

import com.ecommerce.web.product.application.shared.factory.ProductFactory;
import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdUseCaseTest {
    @InjectMocks
    private GetProductByIdUseCase getProductByIdUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductByIdRequest getProductByIdRequest;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductByIdRequest = new GetProductByIdRequest(1L);
    }

    @Test
    void execute_ShouldReturnProductWhenFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(factory.product1));

        // Act
        GetProductByIdResponse response = getProductByIdUseCase.execute(getProductByIdRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProduct());
        assertEquals("Laptop", response.getProduct().getName());
        assertEquals(999.99, response.getProduct().getPrice());
    }

    @Test
    void execute_ShouldReturnNullWhenProductNotFound() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        GetProductByIdResponse response = new GetProductByIdUseCase(productRepository).execute(new GetProductByIdRequest(999L));

        // Assert
        assertNotNull(response);
        assertNull(response.getProduct());
    }

    @Test
    void execute_ShouldReturnProductWithCorrectId() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(factory.product1));

        // Act
        GetProductByIdResponse response = getProductByIdUseCase.execute(getProductByIdRequest);

        // Assert
        assertEquals(1L, response.getProduct().getId());
    }
}

