package com.ecommerce.web.product.application.query.getByPriceRange;

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
class GetProductsByPriceRangeUseCaseTest {
    @InjectMocks
    private GetProductsByPriceRangeUseCase getProductsByPriceRangeUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductsByPriceRangeRequest getProductsByPriceRangeRequest;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductsByPriceRangeRequest = new GetProductsByPriceRangeRequest(20.0, 50.0);
        products = Arrays.asList(factory.product2);
    }

    @Test
    void execute_ShouldReturnProductsWithinPriceRange() {
        // Arrange
        when(productRepository.findByPriceRange(20.0, 50.0)).thenReturn(products);

        // Act
        GetProductsByPriceRangeResponse response = getProductsByPriceRangeUseCase.execute(getProductsByPriceRangeRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(1, response.getProducts().size());
        assertTrue(response.getProducts().get(0).getPrice() >= 20.0 &&
                   response.getProducts().get(0).getPrice() <= 50.0);
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoProductsInRange() {
        // Arrange
        when(productRepository.findByPriceRange(1000.0, 2000.0)).thenReturn(Arrays.asList());

        // Act
        GetProductsByPriceRangeResponse response = new GetProductsByPriceRangeUseCase(productRepository)
                .execute(new GetProductsByPriceRangeRequest(1000.0, 2000.0));

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductWithCorrectPriceInRange() {
        // Arrange
        when(productRepository.findByPriceRange(20.0, 50.0)).thenReturn(products);

        // Act
        GetProductsByPriceRangeResponse response = getProductsByPriceRangeUseCase.execute(getProductsByPriceRangeRequest);

        // Assert
        assertEquals(25.99, response.getProducts().get(0).getPrice());
        assertTrue(response.getProducts().get(0).getPrice() >= 20.0);
        assertTrue(response.getProducts().get(0).getPrice() <= 50.0);
    }
}

