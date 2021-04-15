package com.salesmicroservices.product.modules.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {

    @JsonProperty("id")
    public String salesId;

    public List<SalesProductsResponse> products;
}
