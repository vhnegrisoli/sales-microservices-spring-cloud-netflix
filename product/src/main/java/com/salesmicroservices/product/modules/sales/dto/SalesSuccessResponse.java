package com.salesmicroservices.product.modules.sales.dto;

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
public class SalesSuccessResponse {

    private List<SalesResponse> salesResponse;

    private boolean success;

    public static SalesSuccessResponse createSuccessResponse(List<SalesResponse> salesResponse) {
        return SalesSuccessResponse
            .builder()
            .salesResponse(salesResponse)
            .success(true)
            .build();
    }

    public static SalesSuccessResponse createFailResponse() {
        return SalesSuccessResponse
            .builder()
            .salesResponse(Collections.emptyList())
            .success(false)
            .build();
    }
}
