package com.ukm.ksr.logistics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank(message = "Password lama tidak boleh kosong")
    private String oldPassword;

    @NotBlank(message = "Password baru tidak boleh kosong")
    @Size(min = 6, max = 100, message = "Password baru harus antara 6-100 karakter")
    private String newPassword;

    @NotBlank(message = "Konfirmasi password tidak boleh kosong")
    private String confirmPassword;

    // Constructors
    public ChangePasswordRequest() {}

    public ChangePasswordRequest(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}