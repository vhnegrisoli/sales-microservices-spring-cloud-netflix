package com.salesmicroservices.sales.modules.product.dto;

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
    private CategoryResponse category;
    private SupplierResponse supplier;
}
