package com.salesmicroservices.sales.modules.sales.dto;

import com.salesmicroservices.sales.modules.product.dto.ProductResponse;
import com.salesmicroservices.sales.modules.sales.document.SalesProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIdQuantityResponse {

    private Integer productId;

    private Integer quantity;

    public static ProductIdQuantityResponse convertFrom(SalesProduct salesProduct) {
        return ProductIdQuantityResponse
            .builder()
            .productId(salesProduct.getProduct().getProductId())
            .quantity(salesProduct.getQuantity())
            .build();
    }
}
