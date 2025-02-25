package com.wiormiw.digilott_20.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        @JsonInclude(JsonInclude.Include.NON_NULL) String email
) {}
