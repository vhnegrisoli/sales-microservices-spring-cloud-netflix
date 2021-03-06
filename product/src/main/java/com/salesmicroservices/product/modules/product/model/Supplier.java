package com.salesmicroservices.product.modules.product.model;

import com.salesmicroservices.product.modules.product.dto.SupplierDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUPPLIER")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EIN_CNPJ", nullable = false, unique = true)
    private String einCnpj;

    public static Supplier convertFrom(SupplierDto request) {
        return Supplier
            .builder()
            .name(request.getName())
            .einCnpj(request.getEinCnpj())
            .build();
    }

    public static Supplier fromId(Integer id) {
        return Supplier
            .builder()
            .id(id)
            .build();
    }
}
