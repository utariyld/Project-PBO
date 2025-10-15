package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.ChangePasswordRequest;
import com.ukm.ksr.logistics.dto.UserRequest;
import com.ukm.ksr.logistics.dto.UserResponse;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan: " + username));
    }

    public UserResponse registerUser(UserRequest userRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username sudah digunakan");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email sudah digunakan");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public UserResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        return new UserResponse(user);
    }

    public UserResponse updateUserProfile(String username, UserRequest userRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Check if new username is already taken by another user
        if (!user.getUsername().equals(userRequest.getUsername()) && 
            userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username sudah digunakan");
        }

        // Check if new email is already taken by another user
        if (!user.getEmail().equals(userRequest.getEmail()) && 
            userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email sudah digunakan");
        }

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());

        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Password lama tidak benar");
        }

        // Verify new password confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Konfirmasi password tidak cocok");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        userRepository.delete(user);
    }

    // Admin methods
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        return new UserResponse(user);
    }

    public List<UserResponse> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse updateUserRole(Long userId, User.Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        userRepository.delete(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}