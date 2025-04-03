package com.example.blogbackend.controller;

import com.example.blogbackend.dto.LoginDto;
import com.example.blogbackend.dto.RegisterDto;
import com.example.blogbackend.dto.UserProfileDto;
import com.example.blogbackend.model.User;
import com.example.blogbackend.security.JwtTokenProvider;

import com.example.blogbackend.service.AuthServiceImpl;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthServiceImpl authService, 
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider tokenProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        if (authService.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = authService.register(registerDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);
        
        // Get user details
        User user = authService.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(new JwtAuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail()
        ));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getCurrentUserProfile(
            @AuthenticationPrincipal User user) {
        UserProfileDto profileDto = new UserProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
        return ResponseEntity.ok(profileDto);
    }

    // Helper class for JWT response
    private static class JwtAuthResponse {
        private String token;
        private Long id;
        private String name;
        private String email;

        public JwtAuthResponse(String token, Long id, String name, String email) {
            this.token = token;
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters
        public String getToken() { return token; }
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }
}