package com.salesmicroservices.sales.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSuccessResponse {

    private List<ProductResponse> productsResponse;

    private boolean success;

    public static ProductSuccessResponse createSuccessResponse(List<ProductResponse> productsResponse) {
        return ProductSuccessResponse
            .builder()
            .productsResponse(productsResponse)
            .success(true)
            .build();
    }

    public static ProductSuccessResponse createFailResponse() {
        return ProductSuccessResponse
            .builder()
            .productsResponse(Collections.emptyList())
            .success(false)
            .build();
    }
}
