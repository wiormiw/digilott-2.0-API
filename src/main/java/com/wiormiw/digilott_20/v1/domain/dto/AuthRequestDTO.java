package com.wiormiw.digilott_20.v1.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank String username,
        @NotBlank String password
) {}
