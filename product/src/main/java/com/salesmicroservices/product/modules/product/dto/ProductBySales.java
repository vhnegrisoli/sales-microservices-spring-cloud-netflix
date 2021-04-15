package com.salesmicroservices.product.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBySales {

    private Integer productId;
    private Long totalSales;
    private List<String> sales;
}
