package com.ukm.ksr.logistics.dto;

import com.ukm.ksr.logistics.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ItemRequest {
    @NotBlank(message = "Nama barang tidak boleh kosong")
    @Size(max = 100, message = "Nama barang maksimal 100 karakter")
    private String name;

    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;

    @NotNull(message = "Kategori tidak boleh kosong")
    private Item.ItemCategory category;

    private Item.ItemStatus status = Item.ItemStatus.AVAILABLE;

    @NotNull(message = "Jumlah total tidak boleh kosong")
    @PositiveOrZero(message = "Jumlah total harus positif atau nol")
    private Integer totalQuantity;

    @PositiveOrZero(message = "Jumlah tersedia harus positif atau nol")
    private Integer availableQuantity;

    @Size(max = 100, message = "Lokasi maksimal 100 karakter")
    private String location;

    @Size(max = 100, message = "Brand maksimal 100 karakter")
    private String brand;

    @Size(max = 50, message = "Model maksimal 50 karakter")
    private String model;

    @Size(max = 50, message = "Serial number maksimal 50 karakter")
    private String serialNumber;

    // Constructors
    public ItemRequest() {}

    public ItemRequest(String name, String description, Item.ItemCategory category, Integer totalQuantity) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
    }

    // Getters and Setters
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

    public Item.ItemStatus getStatus() {
        return status;
    }

    public void setStatus(Item.ItemStatus status) {
        this.status = status;
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
}