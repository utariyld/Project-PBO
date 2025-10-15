package com.ukm.ksr.logistics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public class MedicalScheduleRequest {
    @NotBlank(message = "Judul tidak boleh kosong")
    @Size(max = 100, message = "Judul maksimal 100 karakter")
    private String title;

    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;

    @NotNull(message = "Waktu mulai tidak boleh kosong")
    private LocalDateTime startDateTime;

    @NotNull(message = "Waktu selesai tidak boleh kosong")
    private LocalDateTime endDateTime;

    @NotBlank(message = "Lokasi tidak boleh kosong")
    @Size(max = 200, message = "Lokasi maksimal 200 karakter")
    private String location;

    private Set<Long> assignedMemberIds;

    @Size(max = 500, message = "Catatan maksimal 500 karakter")
    private String notes;

    // Constructors
    public MedicalScheduleRequest() {}

    public MedicalScheduleRequest(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Long> getAssignedMemberIds() {
        return assignedMemberIds;
    }

    public void setAssignedMemberIds(Set<Long> assignedMemberIds) {
        this.assignedMemberIds = assignedMemberIds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}