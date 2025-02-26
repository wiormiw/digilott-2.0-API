package com.wiormiw.digilott_20.v1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email,
        @NotBlank @Pattern(regexp = "\\d+", message = "Must contain only numbers") String nik,
        @NotBlank String fullName,
        @NotBlank String province,
        @NotBlank String city,
        @NotBlank String countryCode,
        @NotBlank @Pattern(regexp = "\\d+", message = "Must contain only numbers") String phoneNumber
) {}
