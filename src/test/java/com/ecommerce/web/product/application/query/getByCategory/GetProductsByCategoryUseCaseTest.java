package com.ecommerce.web.product.application.query.getByCategory;

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
class GetProductsByCategoryUseCaseTest {
    @InjectMocks
    private GetProductsByCategoryUseCase getProductsByCategoryUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductsByCategoryRequest getProductsByCategoryRequest;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductsByCategoryRequest = new GetProductsByCategoryRequest("Electronics");
        products = Arrays.asList(factory.product1, factory.product2);
    }

    @Test
    void execute_ShouldReturnProductsWithMatchingCategory() {
        // Arrange
        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        // Act
        GetProductsByCategoryResponse response = getProductsByCategoryUseCase.execute(getProductsByCategoryRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(2, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnOnlyProductsFromSpecificCategory() {
        // Arrange
        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        // Act
        GetProductsByCategoryResponse response = getProductsByCategoryUseCase.execute(getProductsByCategoryRequest);

        // Assert
        assertTrue(response.getProducts().stream()
                .allMatch(p -> p.getCategory().equals("Electronics")));
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoProductsInCategory() {
        // Arrange
        when(productRepository.findByCategory("NonExistent")).thenReturn(Arrays.asList());

        // Act
        GetProductsByCategoryResponse response = new GetProductsByCategoryUseCase(productRepository)
                .execute(new GetProductsByCategoryRequest("NonExistent"));

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductsWithCorrectAttributes() {
        // Arrange
        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        // Act
        GetProductsByCategoryResponse response = getProductsByCategoryUseCase.execute(getProductsByCategoryRequest);

        // Assert
        assertEquals("Laptop", response.getProducts().get(0).getName());
        assertEquals("Mouse", response.getProducts().get(1).getName());
    }
}

