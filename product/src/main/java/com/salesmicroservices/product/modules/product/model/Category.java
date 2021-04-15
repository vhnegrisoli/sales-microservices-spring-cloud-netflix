package com.salesmicroservices.product.modules.product.model;

import com.salesmicroservices.product.modules.product.dto.CategoryDto;
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
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;

    public static Category convertFrom(CategoryDto categoryDto) {
        return Category
            .builder()
            .id(categoryDto.getId())
            .description(categoryDto.getDescription())
            .build();
    }

    public static Category fromId(Integer id) {
        return Category
            .builder()
            .id(id)
            .build();
    }
}
