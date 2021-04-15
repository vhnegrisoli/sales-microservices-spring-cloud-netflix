package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByDescriptionContainingIgnoreCase(String description);

    Boolean existsByDescription(String description);

    Boolean existsByDescriptionAndIdNot(String description, Integer id);
}
