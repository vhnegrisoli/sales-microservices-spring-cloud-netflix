package com.salesmicroservices.product.modules.product.dto;

import com.salesmicroservices.product.modules.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer productId;
    private String productDescription;
    private Double price;
    private Integer quantityAvailable;
    private CategoryDto category;
    private SupplierDto supplier;

    public static ProductResponse convertFrom(Product product) {
        return ProductResponse
            .builder()
            .productId(product.getId())
            .productDescription(product.getDescription())
            .price(product.getPrice())
            .quantityAvailable(product.getQuantityAvailable())
            .category(CategoryDto.convertFrom(product.getCategory()))
            .supplier(SupplierDto.convertFrom(product.getSupplier()))
            .build();
    }
}
