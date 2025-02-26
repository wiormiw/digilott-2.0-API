package com.wiormiw.digilott_20.v1.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "nik", unique = true, nullable = false, length = 16)
    private String nik;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "province", nullable = false, length = 32)
    private String province;

    @Column(name = "city", nullable = false, length = 32)
    private String city;

    @Column(name = "country_code", nullable = false, length = 4)
    private String countryCode;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    public String getFullPhoneNumber() {
        return countryCode + phoneNumber;
    }
}
