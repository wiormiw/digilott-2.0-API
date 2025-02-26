package com.wiormiw.digilott_20.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wiormiw.digilott_20.v1.domain.models.Room;

import java.math.BigDecimal;
import java.util.UUID;

public record RoomResponseDTO(
        UUID id,
        String name,
        String description,
        Room.RoomStatus status,
        @JsonInclude(JsonInclude.Include.NON_NULL) Integer minParticipants,
        @JsonInclude(JsonInclude.Include.NON_NULL) Integer maxParticipants,
        @JsonInclude(JsonInclude.Include.NON_NULL) BigDecimal participationCost
) {}
