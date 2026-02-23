package com.ecommerce.web.product.application.query.getAll;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetAllProductsUseCase {
    private final ProductRepository productRepository;

    public GetAllProductsResponse execute(){
            return new GetAllProductsResponse(productRepository.findAll());
    }
}
