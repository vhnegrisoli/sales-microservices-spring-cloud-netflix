package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByDescription(String description);

    Boolean existsByDescription(String description);

    Boolean existsByCategoryId(Integer categoryId);

    Boolean existsBySupplierId(Integer categoryId);
}