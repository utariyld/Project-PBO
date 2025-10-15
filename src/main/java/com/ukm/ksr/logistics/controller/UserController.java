package com.ukm.ksr.logistics.controller;

import com.ukm.ksr.logistics.dto.*;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "User management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Get current user's profile information")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        try {
            UserResponse userResponse = userService.getUserProfile(authentication.getName());
            return ResponseEntity.ok(new ApiResponse("Profil berhasil diambil", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile", description = "Update current user's profile information")
    public ResponseEntity<?> updateProfile(Authentication authentication, @Valid @RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.updateUserProfile(authentication.getName(), userRequest);
            return ResponseEntity.ok(new ApiResponse("Profil berhasil diperbarui", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change password", description = "Change current user's password")
    public ResponseEntity<?> changePassword(Authentication authentication, @Valid @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(authentication.getName(), request);
            return ResponseEntity.ok(new ApiResponse("Password berhasil diubah", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/account")
    @Operation(summary = "Delete account", description = "Delete current user's account")
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        try {
            userService.deleteAccount(authentication.getName());
            return ResponseEntity.ok(new ApiResponse("Akun berhasil dihapus", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Admin endpoints
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users (Admin only)")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse("Daftar pengguna berhasil diambil", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Get user by ID (Admin only)")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserResponse userResponse = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("Pengguna berhasil diambil", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Search users", description = "Search users by keyword (Admin only)")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword) {
        try {
            List<UserResponse> users = userService.searchUsers(keyword);
            return ResponseEntity.ok(new ApiResponse("Hasil pencarian pengguna", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get users by role", description = "Get users by role (Admin only)")
    public ResponseEntity<?> getUsersByRole(@PathVariable User.Role role) {
        try {
            List<UserResponse> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(new ApiResponse("Pengguna berdasarkan role", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role", description = "Update user role (Admin only)")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestParam User.Role role) {
        try {
            UserResponse userResponse = userService.updateUserRole(id, role);
            return ResponseEntity.ok(new ApiResponse("Role pengguna berhasil diperbarui", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete user by ID (Admin only)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("Pengguna berhasil dihapus", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}