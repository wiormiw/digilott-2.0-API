    package com.wiormiw.digilott_20.v1.web.controller;

    import com.wiormiw.digilott_20.v1.application.service.UserService;
    import com.wiormiw.digilott_20.v1.domain.dto.*;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.UUID;

    @RestController
    public class UserController extends BaseV1Controller {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/users")
        public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @GetMapping("/users/profiles")
        public ResponseEntity<List<UserProfileDTO>> getAllUsersWithProfiles() {
            return ResponseEntity.ok(userService.getAllUsersWithProfiles());
        }

        @GetMapping("/users/{id}")
        public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
            return ResponseEntity.ok(userService.getById(id));
        }

        @GetMapping("/users/{id}/profile")
        public ResponseEntity<UserProfileDTO> getProfileByUserId(@PathVariable UUID id) {
            return ResponseEntity.ok(userService.getProfileByUserId(id));
        }

        @PutMapping("/users/{id}")
        public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO dto) {
            return ResponseEntity.ok(userService.updateUser(id, dto));
        }

        @PutMapping("/users/{id}/profile")
        public ResponseEntity<ProfileDTO> updateProfile(@PathVariable UUID id, @RequestBody ProfileUpdateDTO dto) {
            return ResponseEntity.ok(userService.updateProfile(id, dto));
        }

        @DeleteMapping("/users/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }
