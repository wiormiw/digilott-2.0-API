package com.wiormiw.digilott_20.v1.infrastructure.repository;

import com.wiormiw.digilott_20.v1.domain.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
