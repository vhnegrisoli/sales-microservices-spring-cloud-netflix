package com.salesmicroservices.product.modules.product.dto;

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
}
