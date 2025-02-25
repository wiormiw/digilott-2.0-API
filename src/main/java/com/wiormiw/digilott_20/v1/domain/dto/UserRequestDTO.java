package com.wiormiw.digilott_20.v1.domain.dto;

public record UserRequestDTO(
        String username,
        String password,
        String email,
        String nik,
        String fullName,
        String province,
        String city,
        String countryCode,
        String phoneNumber
) {}
