package com.salesmicroservices.product.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesConfirmatioMessage {

    private String salesId;
    private boolean confirmed;
    private String cause;
}
