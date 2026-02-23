package com.ecommerce.web.product.application.query.getByMaxPrice;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetProductsByMaxPriceUseCase {
    private final ProductRepository productRepository;

    public GetProductsByMaxPriceResponse execute(GetProductsByMaxPriceRequest request) {
        List<Product> products = productRepository.findByMaxPrice(request.getMaxPrice());

        return new GetProductsByMaxPriceResponse(products);
    }
}

