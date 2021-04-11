package com.salesmicroservices.auth.modules.user.repository;

import com.salesmicroservices.auth.modules.user.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByDescription(String description);
}
