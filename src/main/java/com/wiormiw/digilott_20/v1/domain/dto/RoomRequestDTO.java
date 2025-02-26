package com.wiormiw.digilott_20.v1.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotBlank String name,
        String description,
        @NotNull @Min(1) int minParticipants,
        @NotNull @Min(1) int maxParticipants,
        @NotNull @Min(0) BigDecimal participationCost
) {}

