package com.salesmicroservices.sales.modules.sales.dto;

import com.salesmicroservices.sales.modules.sales.document.SalesProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockMessage {

    private String salesId;
    private Integer productId;
    private Integer quantity;

    public static ProductStockMessage convertFrom(String salesId, SalesProduct product) {
        return ProductStockMessage
            .builder()
            .salesId(salesId)
            .productId(product.getProduct().getProductId())
            .quantity(product.getQuantity())
            .build();
    }
}
