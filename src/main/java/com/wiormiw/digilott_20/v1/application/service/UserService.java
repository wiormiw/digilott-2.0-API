package com.wiormiw.digilott_20.v1.application.service;

import com.wiormiw.digilott_20.v1.domain.dto.*;
import com.wiormiw.digilott_20.v1.domain.exception.ResourceNotFoundException;
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
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public List<UserProfileDTO> getAllUsersWithProfiles() {
        return profileRepository.findAll().stream()
                .map(profile -> new UserProfileDTO(
                        new UserResponseDTO(profile.getUser().getId(), profile.getUser().getUsername(), profile.getUser().getEmail()),
                        new ProfileDTO(profile.getNik(), profile.getFullName(), profile.getProvince(), profile.getCity(), profile.getFullPhoneNumber())
                ))
                .collect(Collectors.toList());
    }

    public UserResponseDTO getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserProfileDTO getProfileByUserId(UUID userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for User ID " + userId + " not found"));

        return new UserProfileDTO(
                new UserResponseDTO(profile.getUser().getId(), profile.getUser().getUsername(), profile.getUser().getEmail()),
                new ProfileDTO(profile.getNik(), profile.getFullName(), profile.getProvince(), profile.getCity(), profile.getFullPhoneNumber())
        );
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

        userRepository.save(user);
        profileRepository.save(profile);

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional
    public UserResponseDTO updateUser(UUID userId, UserRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        user.setUsername(dto.username());
        user.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        userRepository.save(user);
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional
    public ProfileDTO updateProfile(UUID userId, ProfileUpdateDTO dto) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for User ID " + userId + " not found"));

        profile.setNik(dto.nik());
        profile.setFullName(dto.fullName());
        profile.setProvince(dto.province());
        profile.setCity(dto.city());
        profile.setCountryCode(dto.countryCode());
        profile.setPhoneNumber(dto.phoneNumber());

        profileRepository.save(profile);
        return new ProfileDTO(profile.getNik(), profile.getFullName(), profile.getProvince(), profile.getCity(), profile.getFullPhoneNumber());
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}

