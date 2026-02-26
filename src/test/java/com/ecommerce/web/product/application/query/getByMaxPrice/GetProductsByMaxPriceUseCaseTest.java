package com.ecommerce.web.product.application.query.getByMaxPrice;

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
class GetProductsByMaxPriceUseCaseTest {
    @InjectMocks
    private GetProductsByMaxPriceUseCase getProductsByMaxPriceUseCase;

    @Mock
    private ProductRepository productRepository;

    private ProductFactory factory;
    private GetProductsByMaxPriceRequest getProductsByMaxPriceRequest;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        getProductsByMaxPriceRequest = new GetProductsByMaxPriceRequest(50.0);
        products = Arrays.asList(factory.product2);
    }

    @Test
    void execute_ShouldReturnProductsLessThanMaxPrice() {
        // Arrange
        when(productRepository.findByMaxPrice(50.0)).thenReturn(products);

        // Act
        GetProductsByMaxPriceResponse response = getProductsByMaxPriceUseCase.execute(getProductsByMaxPriceRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(1, response.getProducts().size());
        assertTrue(response.getProducts().get(0).getPrice() <= 50.0);
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoPricesBelowMaximum() {
        // Arrange
        when(productRepository.findByMaxPrice(10.0)).thenReturn(Arrays.asList());

        // Act
        GetProductsByMaxPriceResponse response = new GetProductsByMaxPriceUseCase(productRepository)
                .execute(new GetProductsByMaxPriceRequest(10.0));

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getProducts().size());
    }

    @Test
    void execute_ShouldReturnProductWithCorrectPrice() {
        // Arrange
        when(productRepository.findByMaxPrice(50.0)).thenReturn(products);

        // Act
        GetProductsByMaxPriceResponse response = getProductsByMaxPriceUseCase.execute(getProductsByMaxPriceRequest);

        // Assert
        assertEquals(25.99, response.getProducts().get(0).getPrice());
    }
}

