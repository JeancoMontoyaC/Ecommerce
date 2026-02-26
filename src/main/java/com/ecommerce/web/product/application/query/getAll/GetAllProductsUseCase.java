package com.ecommerce.web.product.application.query.getAll;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class GetAllProductsUseCase {
    private final ProductRepository productRepository;

    public GetAllProductsResponse execute(){
        log.info("Getting all products");
        List<Product> products = productRepository.findAll();

        log.info("Products found: {}", products.size());
        return new GetAllProductsResponse(products);
    }
}
