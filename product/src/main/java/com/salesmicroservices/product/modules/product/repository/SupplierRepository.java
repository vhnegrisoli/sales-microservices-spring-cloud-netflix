package com.salesmicroservices.product.modules.product.repository;

import com.salesmicroservices.product.modules.product.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByName(String name);

    Optional<Supplier> findByEinCnpj(String einCnpj);

    Boolean existsByName(String name);

    Boolean existsByEinCnpj(String einCnpj);
}
