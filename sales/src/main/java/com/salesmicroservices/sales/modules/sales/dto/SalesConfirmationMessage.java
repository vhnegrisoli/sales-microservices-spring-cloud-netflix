package com.salesmicroservices.sales.modules.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesConfirmationMessage {

    private String salesId;
    private Boolean confirmed;
    private String cause;
}
