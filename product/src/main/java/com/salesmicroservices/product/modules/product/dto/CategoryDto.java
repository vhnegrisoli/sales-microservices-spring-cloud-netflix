package com.salesmicroservices.product.modules.product.dto;

import com.salesmicroservices.product.modules.product.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;
    private String description;

    public static CategoryDto convertFrom(Category category) {
        return CategoryDto
            .builder()
            .id(category.getId())
            .description(category.getDescription())
            .build();
    }
}
