package com.ecommerce.web.product.application.query.getById;

import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdResponse execute(GetProductByIdRequest request) {
        Optional<Product> product = productRepository.findById(request.getId());

        return new GetProductByIdResponse(product);
    }

}
