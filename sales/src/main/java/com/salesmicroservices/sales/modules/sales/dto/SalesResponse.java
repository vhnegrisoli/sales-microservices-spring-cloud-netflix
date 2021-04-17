package com.salesmicroservices.sales.modules.sales.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesmicroservices.sales.modules.sales.document.SalesBuyer;
import com.salesmicroservices.sales.modules.sales.enums.SalesStatus;
import com.salesmicroservices.sales.modules.sales.document.Sales;
import com.salesmicroservices.sales.modules.sales.document.SalesProduct;
import com.salesmicroservices.sales.modules.sales.util.DateConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {

    private String id;

    @JsonFormat(pattern = DateConstants.LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonFormat(pattern = DateConstants.LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime lastUpdate = LocalDateTime.now();

    private SalesStatus status = SalesStatus.PENDING;

    private List<SalesProduct> products = Collections.emptyList();

    private BigDecimal salesTotal = BigDecimal.ZERO;

    private List<String> cancelationCause = Collections.emptyList();

    private SalesBuyer buyer = new SalesBuyer();

    public static SalesResponse convertFrom(Sales sales) {
        return SalesResponse
            .builder()
            .id(sales.getId())
            .createdAt(sales.getCreatedAt())
            .lastUpdate(sales.getLastUpdate())
            .status(sales.getStatus())
            .products(sales.getProducts())
            .buyer(sales.getBuyer())
            .salesTotal(sales.calculateTotalSales())
            .cancelationCause(sales.getCancelationCause())
            .build();
    }
}
