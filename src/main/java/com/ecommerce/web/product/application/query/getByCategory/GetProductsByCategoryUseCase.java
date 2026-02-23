package com.ecommerce.web.product.application.query.getByCategory;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetProductsByCategoryUseCase {
    private final ProductRepository productRepository;

    public GetProductsByCategoryResponse execute(GetProductsByCategoryRequest request) {
        List<Product> products = productRepository.findByCategory(request.getCategory());

        return new GetProductsByCategoryResponse(products);
    }
}

