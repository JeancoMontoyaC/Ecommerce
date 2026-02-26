package com.ecommerce.web.product.application.query.getAll;

import com.ecommerce.web.product.application.shared.factory.ProductFactory;
import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {
    @InjectMocks
    private GetAllProductsUseCase getAllProductsUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        products = Arrays.asList(factory.product1, factory.product2);
    }

    @Test
    void execute_ShouldReturnAllProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(products);

        // Act
        GetAllProductsResponse response = getAllProductsUseCase.execute();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(2, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductsWithCorrectData() {
        // Arrange
        when(productRepository.findAll()).thenReturn(products);

        // Act
        GetAllProductsResponse response = getAllProductsUseCase.execute();

        // Assert
        assertEquals("Laptop", response.getProducts().get(0).getName());
        assertEquals(999.99, response.getProducts().get(0).getPrice());
        assertEquals("Mouse", response.getProducts().get(1).getName());
        assertEquals(25.99, response.getProducts().get(1).getPrice());
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        GetAllProductsResponse response = getAllProductsUseCase.execute();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(0, response.getProducts().size());
    }
}

