package com.ecommerce.web.product.infrastructure.database.mapper;

import com.ecommerce.web.product.application.command.create.CreateProductRequest;
import com.ecommerce.web.product.application.query.getById.GetProductByIdRequest;
import com.ecommerce.web.product.application.query.getByName.GetProductsByNameRequest;
import com.ecommerce.web.product.application.query.getByMinPrice.GetProductsByMinPriceRequest;
import com.ecommerce.web.product.application.query.getByMaxPrice.GetProductsByMaxPriceRequest;
import com.ecommerce.web.product.application.query.getByPriceRange.GetProductsByPriceRangeRequest;
import com.ecommerce.web.product.application.query.partialUpdate.UpdateProductRequest;
import com.ecommerce.web.product.domain.entity.Product;
import com.ecommerce.web.product.infrastructure.api.dto.ProductDto;
import com.ecommerce.web.product.infrastructure.database.entity.ProductEntity;

import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    public CreateProductRequest toCreateProductRequest(ProductDto productDto){
        return CreateProductRequest.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .available(productDto.getAvailable())
                .shortDescription(productDto.getShortDescription())
                .discountPercentage(productDto.getDiscountPercentage())
                .stock(productDto.getStock())
                .build();
    }

    public GetProductByIdRequest toGetProductByIdRequest(Long id){
        return GetProductByIdRequest.builder()
                .id(id)
                .build();
    }

    public ProductDto mapToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .available(product.getAvailable())
                .shortDescription(product.getShortDescription())
                .discountPercentage(product.getDiscountPercentage())
                .stock(product.getStock())
                .build();
    }

    public Product mapToProduct(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .shortDescription(entity.getShortDescription())
                .price(entity.getPrice())
                .discountPercentage(entity.getDiscountPercentage())
                .imageUrl(entity.getImageUrl())
                .available(entity.getAvailable())
                .stock(entity.getStock())
                .build();
    }

    public Product mapToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .shortDescription(productDto.getShortDescription())
                .price(productDto.getPrice())
                .discountPercentage(productDto.getDiscountPercentage())
                .imageUrl(productDto.getImageUrl())
                .available(productDto.getAvailable())
                .stock(productDto.getStock())
                .build();
    }

    public GetProductsByNameRequest toGetProductsByNameRequest(String name) {
        return GetProductsByNameRequest.builder()
                .name(name)
                .build();
    }

    public GetProductsByMinPriceRequest toGetProductsByMinPriceRequest(Double minPrice) {
        return GetProductsByMinPriceRequest.builder()
                .minPrice(minPrice)
                .build();
    }

    public GetProductsByMaxPriceRequest toGetProductsByMaxPriceRequest(Double maxPrice) {
        return GetProductsByMaxPriceRequest.builder()
                .maxPrice(maxPrice)
                .build();
    }

    public GetProductsByPriceRangeRequest toGetProductsByPriceRangeRequest(Double minPrice, Double maxPrice) {
        return GetProductsByPriceRangeRequest.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }

    public UpdateProductRequest toUpdateProductRequest(Long id, ProductDto product) {
        return UpdateProductRequest
                .builder().
                id(id)
                .product(mapToProduct(product))
                .build();
    }

}
