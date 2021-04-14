package com.salesmicroservices.product.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private Integer id;
    private String productDescription;
    private Double price;
    private Integer quantityAvailable;
    private Integer categoryId;
    private Integer supplierId;
}
