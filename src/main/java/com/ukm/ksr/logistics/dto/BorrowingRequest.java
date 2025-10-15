package com.ukm.ksr.logistics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class BorrowingRequest {
    @NotNull(message = "ID barang tidak boleh kosong")
    private Long itemId;

    @NotNull(message = "Jumlah tidak boleh kosong")
    @Positive(message = "Jumlah harus lebih dari 0")
    private Integer quantity;

    private LocalDateTime expectedReturnAt;

    @Size(max = 500, message = "Tujuan maksimal 500 karakter")
    private String purpose;

    @Size(max = 500, message = "Catatan maksimal 500 karakter")
    private String notes;

    // Constructors
    public BorrowingRequest() {}

    public BorrowingRequest(Long itemId, Integer quantity, String purpose) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.purpose = purpose;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getExpectedReturnAt() {
        return expectedReturnAt;
    }

    public void setExpectedReturnAt(LocalDateTime expectedReturnAt) {
        this.expectedReturnAt = expectedReturnAt;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}