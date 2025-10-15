package com.ukm.ksr.logistics.controller;

import com.ukm.ksr.logistics.dto.ApiResponse;
import com.ukm.ksr.logistics.dto.BorrowingRequest;
import com.ukm.ksr.logistics.dto.BorrowingResponse;
import com.ukm.ksr.logistics.model.BorrowingRecord;
import com.ukm.ksr.logistics.service.BorrowingService;
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

@RestController
@RequestMapping("/api/borrowing")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Borrowing Management", description = "Borrowing and return management APIs")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/request")
    @Operation(summary = "Create borrowing request", description = "Create a new borrowing request")
    public ResponseEntity<?> createBorrowingRequest(Authentication authentication, @Valid @RequestBody BorrowingRequest request) {
        try {
            BorrowingResponse response = borrowingService.createBorrowingRequest(authentication.getName(), request);
            return ResponseEntity.ok(new ApiResponse("Permintaan peminjaman berhasil dibuat", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Approve borrowing request", description = "Approve a borrowing request")
    public ResponseEntity<?> approveBorrowingRequest(@PathVariable Long id, Authentication authentication) {
        try {
            BorrowingResponse response = borrowingService.approveBorrowingRequest(id, authentication.getName());
            return ResponseEntity.ok(new ApiResponse("Permintaan peminjaman disetujui", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Reject borrowing request", description = "Reject a borrowing request")
    public ResponseEntity<?> rejectBorrowingRequest(@PathVariable Long id, @RequestParam(required = false) String reason) {
        try {
            BorrowingResponse response = borrowingService.rejectBorrowingRequest(id, reason);
            return ResponseEntity.ok(new ApiResponse("Permintaan peminjaman ditolak", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Return item", description = "Process item return")
    public ResponseEntity<?> returnItem(@PathVariable Long id, @RequestParam(required = false) String notes) {
        try {
            BorrowingResponse response = borrowingService.returnItem(id, notes);
            return ResponseEntity.ok(new ApiResponse("Barang berhasil dikembalikan", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Get all borrowing records", description = "Get all borrowing records")
    public ResponseEntity<?> getAllBorrowingRecords() {
        try {
            List<BorrowingResponse> records = borrowingService.getAllBorrowingRecords();
            return ResponseEntity.ok(new ApiResponse("Daftar catatan peminjaman", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/my-records")
    @Operation(summary = "Get user's borrowing records", description = "Get current user's borrowing records")
    public ResponseEntity<?> getMyBorrowingRecords(Authentication authentication) {
        try {
            List<BorrowingResponse> records = borrowingService.getBorrowingRecordsByUser(authentication.getName());
            return ResponseEntity.ok(new ApiResponse("Catatan peminjaman Anda", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Get borrowing records by status", description = "Get borrowing records filtered by status")
    public ResponseEntity<?> getBorrowingRecordsByStatus(@PathVariable BorrowingRecord.BorrowingStatus status) {
        try {
            List<BorrowingResponse> records = borrowingService.getBorrowingRecordsByStatus(status);
            return ResponseEntity.ok(new ApiResponse("Catatan peminjaman berdasarkan status", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get borrowing record by ID", description = "Get borrowing record details by ID")
    public ResponseEntity<?> getBorrowingRecordById(@PathVariable Long id) {
        try {
            BorrowingResponse response = borrowingService.getBorrowingRecordById(id);
            return ResponseEntity.ok(new ApiResponse("Detail catatan peminjaman", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Get overdue records", description = "Get overdue borrowing records")
    public ResponseEntity<?> getOverdueRecords() {
        try {
            List<BorrowingResponse> records = borrowingService.getOverdueRecords();
            return ResponseEntity.ok(new ApiResponse("Catatan peminjaman terlambat", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Get borrowing records by date range", description = "Get borrowing records within date range")
    public ResponseEntity<?> getBorrowingRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<BorrowingResponse> records = borrowingService.getBorrowingRecordsByDateRange(startDate, endDate);
            return ResponseEntity.ok(new ApiResponse("Catatan peminjaman dalam rentang tanggal", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}