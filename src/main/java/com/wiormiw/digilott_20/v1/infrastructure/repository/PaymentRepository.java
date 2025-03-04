package com.wiormiw.digilott_20.v1.infrastructure.repository;

import com.wiormiw.digilott_20.v1.domain.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByPayerId(UUID userId);
    List<Payment> findByRoomId(UUID roomId);
}
