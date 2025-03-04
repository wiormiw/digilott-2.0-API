package com.wiormiw.digilott_20.v1.infrastructure.repository;

import com.wiormiw.digilott_20.v1.domain.models.PastWinner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PastWinnerRepository extends JpaRepository<PastWinner, UUID> {
    List<PastWinner> findByRoomId(UUID roomId);
}
