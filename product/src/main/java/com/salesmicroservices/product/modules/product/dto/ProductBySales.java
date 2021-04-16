package com.salesmicroservices.product.modules.product.dto;

import com.salesmicroservices.product.modules.sales.dto.SalesProductsResponse;
import com.salesmicroservices.product.modules.sales.dto.SalesResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBySales {

    private Integer productId;
    private Long totalSales;
    private List<String> sales;

    public static ProductBySales convertFrom(List<SalesResponse> sales, Integer productId) {
        return ProductBySales
            .builder()
            .productId(productId)
            .totalSales(sales
                .stream()
                .map(SalesResponse::getProducts)
                .map(products -> products
                    .stream()
                    .map(SalesProductsResponse::getQuantity)
                    .reduce(BigDecimal.ZERO.longValue(), Long::sum))
                .reduce(BigDecimal.ZERO.longValue(), Long::sum))
            .sales(sales
                .stream()
                .map(SalesResponse::getSalesId)
                .collect(Collectors.toList()))
            .build();
    }
}
