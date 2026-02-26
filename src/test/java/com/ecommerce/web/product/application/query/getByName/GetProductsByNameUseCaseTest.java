package com.ecommerce.web.product.application.query.getByName;

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
class GetProductsByNameUseCaseTest {
    @InjectMocks
    private GetProductsByNameUseCase getProductsByNameUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductsByNameRequest getProductsByNameRequest;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductsByNameRequest = new GetProductsByNameRequest("Laptop");
        products = Arrays.asList(factory.product1);
    }

    @Test
    void execute_ShouldReturnProductsWithMatchingName() {
        // Arrange
        when(productRepository.findByName("Laptop")).thenReturn(products);

        // Act
        GetProductsByNameResponse response = getProductsByNameUseCase.execute(getProductsByNameRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(1, response.getProducts().size());
        assertEquals("Laptop", response.getProducts().get(0).getName());
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoProductsFound() {
        // Arrange
        when(productRepository.findByName("NonExistent")).thenReturn(Arrays.asList());

        // Act
        GetProductsByNameResponse response = new GetProductsByNameUseCase(productRepository)
                .execute(new GetProductsByNameRequest("NonExistent"));

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(0, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductWithCorrectAttributes() {
        // Arrange
        when(productRepository.findByName("Laptop")).thenReturn(products);

        // Act
        GetProductsByNameResponse response = getProductsByNameUseCase.execute(getProductsByNameRequest);

        // Assert
        assertEquals(999.99, response.getProducts().get(0).getPrice());
        assertEquals("Electronics", response.getProducts().get(0).getCategory());
        assertTrue(response.getProducts().get(0).getAvailable());
    }
}

