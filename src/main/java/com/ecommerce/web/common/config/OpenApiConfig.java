package com.ecommerce.web.common.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "E-Commerce API",
                description = "REST API for managing products in the e-commerce system",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Jean Carlo Montoya Castro",
                        email = "jeancarlomontoyac@gmail.com",
                        url = "https://github.com/JeancoMontoyaC"
                )
        )
)
@Configuration
public class OpenApiConfig {
}