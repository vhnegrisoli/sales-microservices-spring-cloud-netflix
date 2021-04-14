package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByDescription(String description);

    Boolean existsByDescription(String description);
}
