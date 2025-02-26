package com.wiormiw.digilott_20.v1.domain.dto;

import jakarta.validation.constraints.Pattern;

public record ProfileUpdateDTO(
        @Pattern(regexp = "\\d+", message = "Must contain only numbers") String nik,
        String fullName,
        String province,
        String city,
        String countryCode,
        @Pattern(regexp = "\\d+", message = "Must contain only numbers") String phoneNumber
) {}
