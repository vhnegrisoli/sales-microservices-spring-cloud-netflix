package com.salesmicroservices.sales.modules.sales.document;

import com.salesmicroservices.sales.modules.product.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesProduct {

    private ProductResponse product = new ProductResponse();

    private Integer quantity = BigDecimal.ONE.intValue();
}
