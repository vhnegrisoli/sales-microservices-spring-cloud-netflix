package com.salesmicroservices.product.modules.product.controller;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.modules.product.dto.CategoryDto;
import com.salesmicroservices.product.modules.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryDto save(@RequestBody CategoryDto request) {
        return categoryService.save(request);
    }

    @PutMapping("{categoryId}")
    public CategoryDto update(@RequestBody CategoryDto request,
                              @PathVariable Integer categoryId) {
        return categoryService.update(request, categoryId);
    }

    @DeleteMapping("{categoryId}")
    public SuccessResponse delete(@PathVariable Integer categoryId) {
        return categoryService.delete(categoryId);
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("{categoryId}")
    public CategoryDto findById(@PathVariable Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @GetMapping("description/{description}")
    public CategoryDto findByDescription(@PathVariable String description) {
        return categoryService.findByDescription(description);
    }
}
