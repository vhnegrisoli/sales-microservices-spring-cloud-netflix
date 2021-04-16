package com.salesmicroservices.product.modules.product.service;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.config.exception.ValidationException;
import com.salesmicroservices.product.modules.product.dto.CategoryDto;
import com.salesmicroservices.product.modules.product.model.Category;
import com.salesmicroservices.product.modules.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public CategoryDto save(CategoryDto request) {
        validateCategoryRequest(request);
        validateCategoryData(request, false);
        var category = Category.convertFrom(request);
        categoryRepository.save(category);
        return findById(category.getId());
    }

    public CategoryDto update(CategoryDto request, Integer id) {
        validateCategoryRequest(request);
        request.setId(id);
        validateCategoryData(request, true);
        var category = Category.convertFrom(request);
        category.setId(id);
        categoryRepository.save(category);
        return findById(category.getId());
    }

    public SuccessResponse delete(Integer categoryId) {
        validateCategoryExistingForProducts(categoryId);
        categoryRepository.deleteById(categoryId);
        return SuccessResponse
            .sendSuccess("Category "
                .concat(String.valueOf(categoryId))
                .concat(" was deleted successfully!")
            );
    }

    public CategoryDto findById(Integer categoryId) {
        return CategoryDto.convertFrom(categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new ValidationException(
                "The category "
                    .concat(String.valueOf(categoryId))
                    .concat(" does not exists."))
            )
        );
    }

    public List<CategoryDto> findAll() {
        return categoryRepository
            .findAll()
            .stream()
            .map(CategoryDto::convertFrom)
            .collect(Collectors.toList());
    }

    public List<CategoryDto> findByDescription(String description) {
        return categoryRepository
            .findByDescriptionContainingIgnoreCase(description)
            .stream()
            .map(CategoryDto::convertFrom)
            .collect(Collectors.toList());
    }

    private void validateCategoryExistingForProducts(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ValidationException("The category ID "
                .concat(String.valueOf(categoryId))
                .concat(" does not exists."));
        }
        if (productService.existsByCategoryId(categoryId)) {
            throw new ValidationException("The category ID "
                .concat(String.valueOf(categoryId))
                .concat(" already exists for a product."));
        }
    }

    private void validateCategoryRequest(CategoryDto request) {
        if (isEmpty(request)) {
            throw new ValidationException("The category request must not be empty.");
        }
    }

    private void validateCategoryData(CategoryDto request, boolean isUpdate) {
        validateNotNullCategoryDescription(request);
        validateNotNullCategoryId(request, isUpdate);
        validateExistingCategoryDescription(request, isUpdate);
    }

    private void validateNotNullCategoryDescription(CategoryDto request) {
        if (isEmpty(request.getDescription())) {
            throw new ValidationException("Category description must not be empty.");
        }
    }

    private void validateNotNullCategoryId(CategoryDto request, boolean isUpdate) {
        if (isUpdate && isEmpty(request.getId())) {
            throw new ValidationException("Category ID must not be empty.");
        }
    }

    private void validateExistingCategoryDescription(CategoryDto request, boolean isUpdate) {
        if (!isUpdate && existsByDescription(request.getDescription())) {
            throw new ValidationException("The category description "
                .concat(request.getDescription())
                .concat(" already exists."));
        }
        if (isUpdate && existsByDescriptionAndDifferentId(request.getDescription(), request.getId())) {
            throw new ValidationException("The category description "
                .concat(request.getDescription())
                .concat(" already exists for another category."));
        }
    }

    public boolean existsByDescription(String categoryDescription) {
        return categoryRepository.existsByDescription(categoryDescription);
    }

    public boolean existsByDescriptionAndDifferentId(String categoryDescription,
                                                     Integer categoryId) {
        return categoryRepository.existsByDescriptionAndIdNot(categoryDescription, categoryId);
    }
}
