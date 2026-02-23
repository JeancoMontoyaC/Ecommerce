package com.ecommerce.web.product.application.query.getByPriceRange;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetProductsByPriceRangeUseCase {
    private final ProductRepository productRepository;

    public GetProductsByPriceRangeResponse execute(GetProductsByPriceRangeRequest request) {
        List<Product> products = productRepository.findByPriceRange(request.getMinPrice(), request.getMaxPrice());

        return new GetProductsByPriceRangeResponse(products);
    }
}

