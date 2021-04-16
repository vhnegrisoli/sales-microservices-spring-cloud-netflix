package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByNameContainingIgnoreCase(String name);

    List<Supplier> findByEinCnpjContainingIgnoreCase(String name);

    Optional<Supplier> findByEinCnpj(String einCnpj);

    Boolean existsByName(String name);

    Boolean existsByEinCnpj(String einCnpj);

    Boolean existsByEinCnpjAndIdNot(String einCnpj, Integer id);
}
