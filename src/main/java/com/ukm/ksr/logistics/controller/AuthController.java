package com.ukm.ksr.logistics.controller;

import com.ukm.ksr.logistics.dto.*;
import com.ukm.ksr.logistics.security.JwtUtil;
import com.ukm.ksr.logistics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.registerUser(userRequest);
            return ResponseEntity.ok(new ApiResponse("Registrasi berhasil", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            
            UserResponse userResponse = userService.getUserProfile(userDetails.getUsername());
            AuthResponse authResponse = new AuthResponse(token, userResponse);

            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Username atau password salah", null));
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate JWT token", description = "Validate if JWT token is still valid")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            boolean isValid = jwtUtil.validateToken(token);
            if (isValid) {
                String username = jwtUtil.extractUsername(token);
                UserResponse userResponse = userService.getUserProfile(username);
                return ResponseEntity.ok(new ApiResponse("Token valid", userResponse));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse("Token tidak valid", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Token tidak valid", null));
        }
    }
}