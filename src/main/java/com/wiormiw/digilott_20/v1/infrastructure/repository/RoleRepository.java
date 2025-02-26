package com.wiormiw.digilott_20.v1.infrastructure.repository;

import com.wiormiw.digilott_20.v1.domain.models.Role;
import com.wiormiw.digilott_20.v1.domain.models.Role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleType name);
}
