package com.ukm.ksr.logistics.dto;

import com.ukm.ksr.logistics.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(max = 50, message = "Username maksimal 50 karakter")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Size(max = 100, message = "Email maksimal 100 karakter")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, max = 100, message = "Password harus antara 6-100 karakter")
    private String password;

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    @Size(max = 100, message = "Nama lengkap maksimal 100 karakter")
    private String fullName;

    @Size(max = 15, message = "Nomor telepon maksimal 15 karakter")
    private String phoneNumber;

    @Size(max = 200, message = "Alamat maksimal 200 karakter")
    private String address;

    private User.Role role = User.Role.MEMBER;

    // Constructors
    public UserRequest() {}

    public UserRequest(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}