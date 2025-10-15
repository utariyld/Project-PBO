package com.ukm.ksr.logistics.dto;

import com.ukm.ksr.logistics.model.Item;

import java.time.LocalDateTime;

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Item.ItemCategory category;
    private String categoryDisplayName;
    private Item.ItemStatus status;
    private String statusDisplayName;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private String location;
    private String brand;
    private String model;
    private String serialNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ItemResponse() {}

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.category = item.getCategory();
        this.categoryDisplayName = item.getCategory().getDisplayName();
        this.status = item.getStatus();
        this.statusDisplayName = item.getStatus().getDisplayName();
        this.totalQuantity = item.getTotalQuantity();
        this.availableQuantity = item.getAvailableQuantity();
        this.location = item.getLocation();
        this.brand = item.getBrand();
        this.model = item.getModel();
        this.serialNumber = item.getSerialNumber();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item.ItemCategory getCategory() {
        return category;
    }

    public void setCategory(Item.ItemCategory category) {
        this.category = category;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public Item.ItemStatus getStatus() {
        return status;
    }

    public void setStatus(Item.ItemStatus status) {
        this.status = status;
    }

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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