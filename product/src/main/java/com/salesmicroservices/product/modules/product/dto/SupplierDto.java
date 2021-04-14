package com.salesmicroservices.product.modules.product.dto;

import com.salesmicroservices.product.modules.product.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Integer id;
    private String name;
    private String einCnpj;

    public static SupplierDto convertFrom(Supplier supplier) {
        return SupplierDto
            .builder()
            .id(supplier.getId())
            .name(supplier.getName())
            .einCnpj(supplier.getEinCnpj())
            .build();
    }
}
