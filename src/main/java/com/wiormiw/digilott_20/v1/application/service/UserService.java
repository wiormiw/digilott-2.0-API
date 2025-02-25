package com.wiormiw.digilott_20.v1.application.service;

import com.wiormiw.digilott_20.v1.domain.dto.*;
import com.wiormiw.digilott_20.v1.domain.models.Profile;
import com.wiormiw.digilott_20.v1.domain.models.User;
import com.wiormiw.digilott_20.v1.infrastructure.repository.ProfileRepository;
import com.wiormiw.digilott_20.v1.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail()))
                .collect(Collectors.toList());
    }

    public List<UserProfileDTO> getAllUsersWithProfiles() {
        return profileRepository.findAll().stream()
                .map(profile -> new UserProfileDTO(
                        new UserResponseDTO(
                                profile.getUser().getId(),
                                profile.getUser().getUsername(),
                                profile.getUser().getEmail()
                        ),
                        new ProfileDTO(
                                profile.getNik(),
                                profile.getFullName(),
                                profile.getProvince(),
                                profile.getCity(),
                                profile.getFullPhoneNumber()
                        )
                ))
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> getById(UUID id) {
        return userRepository.findById(id)
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ));
    }

    public Optional<UserProfileDTO> getProfileByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .map(profile -> new UserProfileDTO(
                        new UserResponseDTO(
                                profile.getUser().getId(),
                                profile.getUser().getUsername(),
                                profile.getUser().getEmail()
                        ),
                        new ProfileDTO(
                                profile.getNik(),
                                profile.getFullName(),
                                profile.getProvince(),
                                profile.getCity(),
                                profile.getFullPhoneNumber()
                        )
                ));
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setEmail(dto.email());

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setNik(dto.nik());
        profile.setFullName(dto.fullName());
        profile.setProvince(dto.province());
        profile.setCity(dto.city());
        profile.setCountryCode(dto.countryCode());
        profile.setPhoneNumber(dto.phoneNumber());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Transactional
    public Optional<UserResponseDTO> updateUser(UUID userId, UserRequestDTO dto) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(dto.username());
            user.setEmail(dto.email());

            if (dto.password() != null && !dto.password().isBlank()) {
                user.setPassword(passwordEncoder.encode(dto.password()));
            }

            userRepository.save(user);
            return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
        });
    }

    @Transactional
    public Optional<ProfileDTO> updateProfile(UUID userId, ProfileUpdateDTO dto) {
        return profileRepository.findByUserId(userId).map(profile -> {
            profile.setNik(dto.nik());
            profile.setFullName(dto.fullName());
            profile.setProvince(dto.province());
            profile.setCity(dto.city());
            profile.setCountryCode(dto.countryCode());
            profile.setPhoneNumber(dto.phoneNumber());

            profileRepository.save(profile);
            return new ProfileDTO(
                    profile.getNik(),
                    profile.getFullName(),
                    profile.getProvince(),
                    profile.getCity(),
                    profile.getFullPhoneNumber()
            );
        });
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
