package com.wiormiw.digilott_20.v1.domain.dto;

public record ProfileDTO(
        String nik,
        String fullName,
        String province,
        String city,
        String phoneNumber
) {}