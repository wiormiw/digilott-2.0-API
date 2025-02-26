    package com.wiormiw.digilott_20.v1.web.controller;

    import com.wiormiw.digilott_20.v1.application.service.UserService;
    import com.wiormiw.digilott_20.v1.domain.dto.*;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.UUID;

    @RestController
    @RequestMapping("/api/v1/users")
    public class UserController extends BaseV1Controller {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping
        public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @GetMapping("/profiles")
        public ResponseEntity<List<UserProfileDTO>> getAllUsersWithProfiles() {
            return ResponseEntity.ok(userService.getAllUsersWithProfiles());
        }

        @GetMapping("/{userId}")
        public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
            return ResponseEntity.ok(userService.getById(userId));
        }

        @GetMapping("/{userId}/profile")
        public ResponseEntity<UserProfileDTO> getProfileByUserId(@PathVariable UUID userId) {
            return ResponseEntity.ok(userService.getProfileByUserId(userId));
        }

        @PutMapping("/{userId}")
        public ResponseEntity<UserResponseDTO> updateUser(@Validated @PathVariable UUID userId, @RequestBody UserRequestDTO dto) {
            return ResponseEntity.ok(userService.updateUser(userId, dto));
        }

        @PutMapping("/{userId}/profile")
        public ResponseEntity<ProfileDTO> updateProfile(@Validated  @PathVariable UUID userId, @RequestBody ProfileUpdateDTO dto) {
            return ResponseEntity.ok(userService.updateProfile(userId, dto));
        }

        @DeleteMapping("/{userId}")
        public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        }
    }
