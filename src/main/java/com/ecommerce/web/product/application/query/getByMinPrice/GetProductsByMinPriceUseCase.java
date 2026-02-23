package com.ecommerce.web.product.application.query.getByMinPrice;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetProductsByMinPriceUseCase {
    private final ProductRepository productRepository;

    public GetProductsByMinPriceResponse execute(GetProductsByMinPriceRequest request) {
        List<Product> products = productRepository.findByMinPrice(request.getMinPrice());

        return new GetProductsByMinPriceResponse(products);
    }
}

