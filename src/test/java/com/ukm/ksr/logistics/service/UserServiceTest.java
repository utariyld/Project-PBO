package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.UserRequest;
import com.ukm.ksr.logistics.dto.UserResponse;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser_Success() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password123");
        userRequest.setFullName("Test User");
        userRequest.setRole(User.Role.MEMBER);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@example.com");
        savedUser.setFullName("Test User");
        savedUser.setRole(User.Role.MEMBER);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserResponse result = userService.registerUser(userRequest);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getFullName());
        assertEquals(User.Role.MEMBER, result.getRole());
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("existinguser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password123");
        userRequest.setFullName("Test User");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(userRequest);
        });

        assertEquals("Username sudah digunakan", exception.getMessage());
    }

    @Test
    public void testGetUserProfile_Success() {
        // Given
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail("test@example.com");
        user.setFullName("Test User");
        user.setRole(User.Role.MEMBER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        UserResponse result = userService.getUserProfile(username);

        // Then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getFullName());
        assertEquals(User.Role.MEMBER, result.getRole());
    }

    @Test
    public void testGetUserProfile_UserNotFound() {
        // Given
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserProfile(username);
        });

        assertEquals("User tidak ditemukan", exception.getMessage());
    }
}