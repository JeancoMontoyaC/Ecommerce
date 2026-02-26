package com.ecommerce.web.product.infrastructure.api;

import com.ecommerce.web.product.application.command.create.CreateProductRequest;
import com.ecommerce.web.product.application.command.create.CreateProductUseCase;
import com.ecommerce.web.product.application.query.getAll.GetAllProductsResponse;
import com.ecommerce.web.product.application.query.getAll.GetAllProductsUseCase;
import com.ecommerce.web.product.application.query.getById.GetProductByIdResponse;
import com.ecommerce.web.product.application.query.getById.GetProductByIdUseCase;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameResponse;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameUseCase;
import com.ecommerce.web.product.application.query.getByCategory.GetProductsByCategoryResponse;
import com.ecommerce.web.product.application.query.getByCategory.GetProductsByCategoryUseCase;
import com.ecommerce.web.product.application.query.getByMinPrice.GetProductsByMinPriceResponse;
import com.ecommerce.web.product.application.query.getByMinPrice.GetProductsByMinPriceUseCase;
import com.ecommerce.web.product.application.query.getByMaxPrice.GetProductsByMaxPriceResponse;
import com.ecommerce.web.product.application.query.getByMaxPrice.GetProductsByMaxPriceUseCase;
import com.ecommerce.web.product.application.query.getByPriceRange.GetProductsByPriceRangeResponse;
import com.ecommerce.web.product.application.query.getByPriceRange.GetProductsByPriceRangeUseCase;
import com.ecommerce.web.product.application.query.partialUpdate.UpdateProductResponse;
import com.ecommerce.web.product.application.query.partialUpdate.UpdateProductUseCase;
import com.ecommerce.web.product.application.shared.factory.ProductFactory;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import com.ecommerce.web.product.infrastructure.database.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private GetAllProductsUseCase getAllProductsUseCase;

    @Mock
    private GetProductByIdUseCase getProductByIdUseCase;

    @Mock
    private GetProductsByNameUseCase getProductsByNameUseCase;

    @Mock
    private GetProductsByCategoryUseCase getProductsByCategoryUseCase;

    @Mock
    private GetProductsByMinPriceUseCase getProductsByMinPriceUseCase;

    @Mock
    private GetProductsByMaxPriceUseCase getProductsByMaxPriceUseCase;

    @Mock
    private GetProductsByPriceRangeUseCase getProductsByPriceRangeUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private ProductMapper productMapper;

    private ProductFactory factory;
    private List<ProductDto> productDtos;

    @BeforeEach
    void setUp() {
        factory = new ProductFactory();
        productDtos = Arrays.asList(factory.productDto1, factory.productDto2);
    }

    @Test
    void getAllProducts() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> products = Arrays.asList(factory.product1, factory.product2);
        GetAllProductsResponse response = new GetAllProductsResponse(products);
        when(getAllProductsUseCase.execute()).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product1)).thenReturn(factory.productDto1);
        when(productMapper.mapToProductDto(factory.product2)).thenReturn(factory.productDto2);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        assertEquals("Laptop", result.getBody().get(0).getName());
        assertEquals("Mouse", result.getBody().get(1).getName());
    }

    @Test
    void getProductById_Found() {
        // Arrange
        GetProductByIdResponse response = new GetProductByIdResponse(Optional.of(factory.product1));
        when(getProductByIdUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product1)).thenReturn(factory.productDto1);

        // Act
        ResponseEntity<ProductDto> result = productController.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Laptop", result.getBody().getName());
        assertEquals(999.99, result.getBody().getPrice());
    }

    @Test
    void getProductById_NotFound() {
        // Arrange
        GetProductByIdResponse response = new GetProductByIdResponse(Optional.empty());
        when(getProductByIdUseCase.execute(any())).thenReturn(response);

        // Act
        ResponseEntity<ProductDto> result = productController.getProductById(999L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getProductsByName() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> foundProducts = Arrays.asList(factory.product1);
        GetProductsByNameResponse response = new GetProductsByNameResponse(foundProducts);
        when(getProductsByNameUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product1)).thenReturn(factory.productDto1);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getProductsByName("Laptop");

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Laptop", result.getBody().get(0).getName());
    }

    @Test
    void getProductsByCategory() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> products = Arrays.asList(factory.product1, factory.product2);
        GetProductsByCategoryResponse response = new GetProductsByCategoryResponse(products);
        when(getProductsByCategoryUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product1)).thenReturn(factory.productDto1);
        when(productMapper.mapToProductDto(factory.product2)).thenReturn(factory.productDto2);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getProductsByCategory("Electronics");

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        assertTrue(result.getBody().stream()
                .allMatch(p -> p.getCategory().equals("Electronics")));
    }

    @Test
    void partialUpdate() {
        // Arrange
        UpdateProductResponse response = new UpdateProductResponse(factory.productUpdated);
        when(updateProductUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.productUpdated)).thenReturn(factory.productDtoUpdated);

        // Act
        ResponseEntity<ProductDto> result = productController.partialUpdate(1L, factory.productDto1);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Laptop Updated", result.getBody().getName());
        assertEquals(899.99, result.getBody().getPrice());
    }

    @Test
    void getProductsByMinPrice() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> foundProducts = Arrays.asList(factory.product1);
        GetProductsByMinPriceResponse response = new GetProductsByMinPriceResponse(foundProducts);
        when(getProductsByMinPriceUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product1)).thenReturn(factory.productDto1);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getProductsByMinPrice(500.0);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertTrue(result.getBody().stream()
                .allMatch(p -> p.getPrice() >= 500.0));
    }

    @Test
    void getProductsByMaxPrice() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> foundProducts = Arrays.asList(factory.product2);
        GetProductsByMaxPriceResponse response = new GetProductsByMaxPriceResponse(foundProducts);
        when(getProductsByMaxPriceUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product2)).thenReturn(factory.productDto2);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getProductsByMaxPrice(50.0);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertTrue(result.getBody().stream()
                .allMatch(p -> p.getPrice() <= 50.0));
    }

    @Test
    void getProductsByPriceRange() {
        // Arrange
        List<com.ecommerce.web.product.domain.entity.Product> foundProducts = Arrays.asList(factory.product2);
        GetProductsByPriceRangeResponse response = new GetProductsByPriceRangeResponse(foundProducts);
        when(getProductsByPriceRangeUseCase.execute(any())).thenReturn(response);
        when(productMapper.mapToProductDto(factory.product2)).thenReturn(factory.productDto2);

        // Act
        ResponseEntity<List<ProductDto>> result = productController.getProductsByPriceRange(20.0, 50.0);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertTrue(result.getBody().stream()
                .allMatch(p -> p.getPrice() >= 20.0 && p.getPrice() <= 50.0));
    }

    @Test
    void createProduct() {
        // Arrange
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .name("Laptop")
                .description("High performance laptop")
                .price(999.99)
                .category("Electronics")
                .available(true)
                .stock(10)
                .imageUrl("https://example.com/laptop.jpg")
                .shortDescription("Professional Laptop")
                .discountPercentage(10.0)
                .build();

        when(productMapper.toCreateProductRequest(factory.productDto1)).thenReturn(createProductRequest);

        // Act
        ResponseEntity<Void> result = productController.createProduct(factory.productDto1);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }
}