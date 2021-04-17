package com.salesmicroservices.sales.modules.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesProductRequest {

    private Integer productId;

    private Integer quantity;
}
