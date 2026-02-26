package com.ecommerce.web.product.application.query.getByMinPrice;

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
class GetProductsByMinPriceUseCaseTest {
    @InjectMocks
    private GetProductsByMinPriceUseCase getProductsByMinPriceUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductsByMinPriceRequest getProductsByMinPriceRequest;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductsByMinPriceRequest = new GetProductsByMinPriceRequest(500.0);
        products = Arrays.asList(factory.product1);
    }

    @Test
    void execute_ShouldReturnProductsGreaterThanMinPrice() {
        // Arrange
        when(productRepository.findByMinPrice(500.0)).thenReturn(products);

        // Act
        GetProductsByMinPriceResponse response = getProductsByMinPriceUseCase.execute(getProductsByMinPriceRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(1, response.getProducts().size());
        assertTrue(response.getProducts().get(0).getPrice() >= 500.0);
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoPricesAboveMinimum() {
        // Arrange
        when(productRepository.findByMinPrice(2000.0)).thenReturn(Arrays.asList());

        // Act
        GetProductsByMinPriceResponse response = new GetProductsByMinPriceUseCase(productRepository)
                .execute(new GetProductsByMinPriceRequest(2000.0));

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductWithCorrectPrice() {
        // Arrange
        when(productRepository.findByMinPrice(500.0)).thenReturn(products);

        // Act
        GetProductsByMinPriceResponse response = getProductsByMinPriceUseCase.execute(getProductsByMinPriceRequest);

        // Assert
        assertEquals(999.99, response.getProducts().get(0).getPrice());
    }
}

