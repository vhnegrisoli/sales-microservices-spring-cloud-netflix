package com.salesmicroservices.product.modules.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {

    @JsonProperty("id")
    public Integer salesId;
}
