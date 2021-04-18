package com.salesmicroservices.sales.modules.sales.dto;

import com.salesmicroservices.sales.modules.sales.document.Sales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesByProductResponse {

    private String salesId;

    private List<ProductIdQuantityResponse> products;

    public static SalesByProductResponse convertFrom(Sales sales) {
        return SalesByProductResponse
            .builder()
            .salesId(sales.getId())
            .products(sales
                .getProducts()
                .stream()
                .map(ProductIdQuantityResponse::convertFrom)
                .collect(Collectors.toList()))
            .build();
    }
}
