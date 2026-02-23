package com.ecommerce.web.product.application.query.getByName;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetProductsByNameUseCase {
    private final ProductRepository productRepository;

    public GetProductsByNameResponse execute(GetProductsByNameRequest request) {
        List<Product> products = productRepository.findByName(request.getName());

        return new GetProductsByNameResponse(products);
    }
}
