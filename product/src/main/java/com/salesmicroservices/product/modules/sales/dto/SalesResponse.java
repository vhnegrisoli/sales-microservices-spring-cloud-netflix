package com.salesmicroservices.product.modules.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {

    public String salesId;

    public List<SalesProductsResponse> products;
}
