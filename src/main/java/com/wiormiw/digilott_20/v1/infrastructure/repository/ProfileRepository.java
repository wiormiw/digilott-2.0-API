package com.wiormiw.digilott_20.v1.infrastructure.repository;

import com.wiormiw.digilott_20.v1.domain.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserId(UUID userId);
}
