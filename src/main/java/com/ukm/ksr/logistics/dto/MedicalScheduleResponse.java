package com.ukm.ksr.logistics.dto;

import com.ukm.ksr.logistics.model.MedicalSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalScheduleResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private MedicalSchedule.ScheduleStatus status;
    private String statusDisplayName;
    private List<UserResponse> assignedMembers;
    private UserResponse createdBy;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public MedicalScheduleResponse() {}

    public MedicalScheduleResponse(MedicalSchedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.startDateTime = schedule.getStartDateTime();
        this.endDateTime = schedule.getEndDateTime();
        this.location = schedule.getLocation();
        this.status = schedule.getStatus();
        this.statusDisplayName = schedule.getStatus().getDisplayName();
        this.assignedMembers = schedule.getAssignedMembers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        this.createdBy = schedule.getCreatedBy() != null ? new UserResponse(schedule.getCreatedBy()) : null;
        this.notes = schedule.getNotes();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public MedicalSchedule.ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(MedicalSchedule.ScheduleStatus status) {
        this.status = status;
    }

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public List<UserResponse> getAssignedMembers() {
        return assignedMembers;
    }

    public void setAssignedMembers(List<UserResponse> assignedMembers) {
        this.assignedMembers = assignedMembers;
    }

    public UserResponse getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponse createdBy) {
        this.createdBy = createdBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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