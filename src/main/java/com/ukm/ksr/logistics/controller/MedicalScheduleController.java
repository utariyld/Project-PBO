package com.ukm.ksr.logistics.controller;

import com.ukm.ksr.logistics.dto.ApiResponse;
import com.ukm.ksr.logistics.dto.MedicalScheduleRequest;
import com.ukm.ksr.logistics.dto.MedicalScheduleResponse;
import com.ukm.ksr.logistics.model.MedicalSchedule;
import com.ukm.ksr.logistics.service.MedicalScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/medical-schedules")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Medical Schedule Management", description = "Medical duty scheduling APIs")
public class MedicalScheduleController {

    @Autowired
    private MedicalScheduleService medicalScheduleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Create medical schedule", description = "Create a new medical duty schedule")
    public ResponseEntity<?> createSchedule(Authentication authentication, @Valid @RequestBody MedicalScheduleRequest request) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.createSchedule(authentication.getName(), request);
            return ResponseEntity.ok(new ApiResponse("Jadwal jaga medis berhasil dibuat", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    @Operation(summary = "Get all medical schedules", description = "Get all medical duty schedules")
    public ResponseEntity<?> getAllSchedules() {
        try {
            List<MedicalScheduleResponse> schedules = medicalScheduleService.getAllSchedules();
            return ResponseEntity.ok(new ApiResponse("Daftar jadwal jaga medis", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medical schedule by ID", description = "Get medical schedule details by ID")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.getScheduleById(id);
            return ResponseEntity.ok(new ApiResponse("Detail jadwal jaga medis", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Update medical schedule", description = "Update medical schedule information")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @Valid @RequestBody MedicalScheduleRequest request) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.updateSchedule(id, request);
            return ResponseEntity.ok(new ApiResponse("Jadwal jaga medis berhasil diperbarui", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Delete medical schedule", description = "Delete medical schedule")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
            medicalScheduleService.deleteSchedule(id);
            return ResponseEntity.ok(new ApiResponse("Jadwal jaga medis berhasil dihapus", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Update schedule status", description = "Update medical schedule status")
    public ResponseEntity<?> updateScheduleStatus(@PathVariable Long id, @RequestParam MedicalSchedule.ScheduleStatus status) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.updateScheduleStatus(id, status);
            return ResponseEntity.ok(new ApiResponse("Status jadwal berhasil diperbarui", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get schedules by status", description = "Get medical schedules filtered by status")
    public ResponseEntity<?> getSchedulesByStatus(@PathVariable MedicalSchedule.ScheduleStatus status) {
        try {
            List<MedicalScheduleResponse> schedules = medicalScheduleService.getSchedulesByStatus(status);
            return ResponseEntity.ok(new ApiResponse("Jadwal berdasarkan status", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get schedules by date range", description = "Get medical schedules within date range")
    public ResponseEntity<?> getSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<MedicalScheduleResponse> schedules = medicalScheduleService.getSchedulesByDateRange(startDate, endDate);
            return ResponseEntity.ok(new ApiResponse("Jadwal dalam rentang tanggal", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/my-schedules")
    @Operation(summary = "Get user's schedules", description = "Get current user's assigned medical schedules")
    public ResponseEntity<?> getMySchedules(Authentication authentication) {
        try {
            List<MedicalScheduleResponse> schedules = medicalScheduleService.getSchedulesForMember(authentication.getName());
            return ResponseEntity.ok(new ApiResponse("Jadwal jaga Anda", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/my-schedules/date-range")
    @Operation(summary = "Get user's schedules by date range", description = "Get current user's schedules within date range")
    public ResponseEntity<?> getMySchedulesByDateRange(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<MedicalScheduleResponse> schedules = medicalScheduleService.getSchedulesForMemberByDateRange(
                    authentication.getName(), startDate, endDate);
            return ResponseEntity.ok(new ApiResponse("Jadwal jaga Anda dalam rentang tanggal", schedules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/assign-members")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Assign members to schedule", description = "Assign members to medical schedule")
    public ResponseEntity<?> assignMembersToSchedule(@PathVariable Long id, @RequestBody Set<Long> memberIds) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.assignMembersToSchedule(id, memberIds);
            return ResponseEntity.ok(new ApiResponse("Anggota berhasil ditugaskan", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{scheduleId}/members/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Remove member from schedule", description = "Remove member from medical schedule")
    public ResponseEntity<?> removeMemberFromSchedule(@PathVariable Long scheduleId, @PathVariable Long memberId) {
        try {
            MedicalScheduleResponse response = medicalScheduleService.removeMemberFromSchedule(scheduleId, memberId);
            return ResponseEntity.ok(new ApiResponse("Anggota berhasil dihapus dari jadwal", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}