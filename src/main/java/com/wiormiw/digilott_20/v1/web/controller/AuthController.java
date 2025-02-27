package com.wiormiw.digilott_20.v1.web.controller;

import com.wiormiw.digilott_20.v1.application.service.UserService;
import com.wiormiw.digilott_20.v1.domain.dto.AuthRequestDTO;
import com.wiormiw.digilott_20.v1.domain.dto.AuthResponseDTO;
import com.wiormiw.digilott_20.v1.domain.dto.UserRequestDTO;
import com.wiormiw.digilott_20.v1.domain.dto.UserResponseDTO;
import com.wiormiw.digilott_20.v1.infrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseV1Controller {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        var userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Validated @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }
}
