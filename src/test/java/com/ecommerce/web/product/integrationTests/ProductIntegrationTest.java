package com.ecommerce.web.product.integrationTests;

import com.ecommerce.web.product.infrastructure.persistence.SpringDataProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
public class ProductIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataProductRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {

        // JSON del request
        String productJson = """
                {
                    "name": "Laptop",
                    "description": "Gaming laptop with RTX 4090",
                    "price": 9.0,
                    "stock": 5,
                    "available": true
                }
                """;

        // Ejecutar request
        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isAccepted());

        var products = repository.findAll();
        assertEquals(1, products.size());
        assertThat(products.get(0).getName()).isEqualTo("Laptop");
        assertThat(products.get(0).getDescription()).isEqualTo("Gaming laptop with RTX 4090");
        assertThat(products.get(0).getPrice()).isEqualTo(9.0);
        assertThat(products.get(0).getStock()).isEqualTo(5);
        assertThat(products.get(0).getAvailable()).isTrue();
    }

    @Test
    void shouldNotCreateProduct_InvalidName_TooShort() throws Exception {
        String productJson = """
                {
                    "name": "AB",
                    "description": "Gaming laptop with RTX 4090",
                    "price": 999.99,
                    "stock": 5,
                    "available": true
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotCreateProduct_InvalidPrice() throws Exception {
        String productJson = """
                {
                    "name": "Laptop",
                    "description": "Gaming laptop with RTX 4090",
                    "price": 1500.99,
                    "stock": 5,
                    "available": true
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldGetAllProducts_Empty() throws Exception {
        mockMvc.perform(get("/api/v1/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldGetAllProducts_WithData() throws Exception {
        String product1Json = """
                {
                    "name": "Laptop",
                    "description": "Gaming laptop with RTX 4090",
                    "price": 9.99,
                    "stock": 5,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Mouse",
                    "description": "Wireless mouse with high precision",
                    "price": 49.99,
                    "stock": 20,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Laptop")))
                .andExpect(jsonPath("$[1].name", equalTo("Mouse")));
    }

    @Test
    void shouldGetProductById() throws Exception {
        String productJson = """
                {
                    "name": "Keyboard",
                    "description": "Mechanical keyboard with RGB lights",
                    "price": 150.99,
                    "stock": 10,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isAccepted());

        var products = repository.findAll();
        Long productId = products.get(0).getId();

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Keyboard")))
                .andExpect(jsonPath("$.price", equalTo(150.99)))
                .andExpect(jsonPath("$.category", equalTo("Accessories")));
    }

    @Test
    void shouldNotGetProductById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldUpdateProductPartially() throws Exception {
        String productJson = """
                {
                    "name": "Monitor",
                    "description": "4K Monitor with 144Hz refresh rate",
                    "price": 450.99,
                    "stock": 8,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isAccepted());

        var products = repository.findAll();
        Long productId = products.get(0).getId();

        String updateJson = """
                {
                    "name": "Monitor Updated",
                    "price": 399.99,
                    "stock": 5
                }
                """;

        mockMvc.perform(patch("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Monitor Updated")))
                .andExpect(jsonPath("$.price", equalTo(399.99)))
                .andExpect(jsonPath("$.stock", equalTo(5)));
    }

    @Test
    void shouldGetProductsByName() throws Exception {
        String product1Json = """
                {
                    "name": "Laptop Gaming",
                    "description": "High performance gaming laptop",
                    "price": 9.99,
                    "stock": 5,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Mouse",
                    "description": "Wireless mouse",
                    "price": 49.99,
                    "stock": 20,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/search/{name}", "Laptop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Laptop Gaming")));
    }


    @Test
    void shouldGetProductsByMinPrice() throws Exception {
        String product1Json = """
                {
                    "name": "Expensive Laptop",
                    "description": "High end laptop",
                    "price": 99.99,
                    "stock": 2,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Cheap Mouse",
                    "description": "Budget mouse",
                    "price": 25.99,
                    "stock": 50,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/filter/min-price/{minPrice}", 90.0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Expensive Laptop")));
    }


    @Test
    void shouldGetProductsByMaxPrice() throws Exception {
        // Crear productos
        String product1Json = """
                {
                    "name": "Expensive Laptop",
                    "description": "High end laptop",
                    "price": 99.99,
                    "stock": 2,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Cheap Mouse",
                    "description": "Budget mouse",
                    "price": 25.99,
                    "stock": 50,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/filter/max-price/{maxPrice}", 90.0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Cheap Mouse")));
    }

    @Test
    void shouldGetProductsByPriceRange() throws Exception {
        // Crear productos
        String product1Json = """
                {
                    "name": "Expensive Laptop",
                    "description": "High end laptop",
                    "price": 9.99,
                    "stock": 2,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Medium Keyboard",
                    "description": "Mid-range keyboard",
                    "price": 150.99,
                    "stock": 15,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        String product3Json = """
                {
                    "name": "Cheap Mouse",
                    "description": "Budget mouse",
                    "price": 25.99,
                    "stock": 50,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product3Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/filter/price-range/{minPrice}/{maxPrice}", 100.0, 500.0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Medium Keyboard")));
    }

    @Test
    void shouldGetProductsByCategory() throws Exception {
        String product1Json = """
                {
                    "name": "Laptop",
                    "description": "Gaming laptop",
                    "price": 9.99,
                    "stock": 2,
                    "available": true,
                    "category": "Electronics"
                }
                """;

        String product2Json = """
                {
                    "name": "Mouse",
                    "description": "Wireless mouse",
                    "price": 49.99,
                    "stock": 20,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        String product3Json = """
                {
                    "name": "Keyboard",
                    "description": "Mechanical keyboard",
                    "price": 150.99,
                    "stock": 10,
                    "available": true,
                    "category": "Accessories"
                }
                """;

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product1Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product2Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product3Json))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/products/filter/category/{category}", "Electronics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Laptop")));
    }
}
