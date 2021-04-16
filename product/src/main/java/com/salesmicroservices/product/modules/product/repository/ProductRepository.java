package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByDescriptionContainingIgnoreCase(String description);

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findBySupplierId(Integer supplierId);

    Boolean existsByDescription(String description);

    Boolean existsByDescriptionAndIdNot(String description, Integer id);

    Boolean existsByCategoryId(Integer categoryId);

    Boolean existsBySupplierId(Integer categoryId);
}