package com.ukm.ksr.logistics.dto;

import com.ukm.ksr.logistics.model.BorrowingRecord;

import java.time.LocalDateTime;

public class BorrowingResponse {
    private Long id;
    private UserResponse borrower;
    private ItemResponse item;
    private Integer quantity;
    private LocalDateTime borrowedAt;
    private LocalDateTime expectedReturnAt;
    private LocalDateTime returnedAt;
    private String purpose;
    private String notes;
    private BorrowingRecord.BorrowingStatus status;
    private String statusDisplayName;
    private UserResponse approvedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public BorrowingResponse() {}

    public BorrowingResponse(BorrowingRecord record) {
        this.id = record.getId();
        this.borrower = new UserResponse(record.getBorrower());
        this.item = new ItemResponse(record.getItem());
        this.quantity = record.getQuantity();
        this.borrowedAt = record.getBorrowedAt();
        this.expectedReturnAt = record.getExpectedReturnAt();
        this.returnedAt = record.getReturnedAt();
        this.purpose = record.getPurpose();
        this.notes = record.getNotes();
        this.status = record.getStatus();
        this.statusDisplayName = record.getStatus().getDisplayName();
        this.approvedBy = record.getApprovedBy() != null ? new UserResponse(record.getApprovedBy()) : null;
        this.createdAt = record.getCreatedAt();
        this.updatedAt = record.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getBorrower() {
        return borrower;
    }

    public void setBorrower(UserResponse borrower) {
        this.borrower = borrower;
    }

    public ItemResponse getItem() {
        return item;
    }

    public void setItem(ItemResponse item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public LocalDateTime getExpectedReturnAt() {
        return expectedReturnAt;
    }

    public void setExpectedReturnAt(LocalDateTime expectedReturnAt) {
        this.expectedReturnAt = expectedReturnAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
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

    public BorrowingRecord.BorrowingStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowingRecord.BorrowingStatus status) {
        this.status = status;
    }

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public UserResponse getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserResponse approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}