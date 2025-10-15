package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.BorrowingRequest;
import com.ukm.ksr.logistics.dto.BorrowingResponse;
import com.ukm.ksr.logistics.model.BorrowingRecord;
import com.ukm.ksr.logistics.model.Item;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.repository.BorrowingRecordRepository;
import com.ukm.ksr.logistics.repository.ItemRepository;
import com.ukm.ksr.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public BorrowingResponse createBorrowingRequest(String username, BorrowingRequest request) {
        User borrower = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));

        // Check if item is available
        if (item.getStatus() != Item.ItemStatus.AVAILABLE) {
            throw new RuntimeException("Barang tidak tersedia untuk dipinjam");
        }

        // Check if quantity is available
        if (item.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("Jumlah barang yang tersedia tidak mencukupi");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBorrower(borrower);
        record.setItem(item);
        record.setQuantity(request.getQuantity());
        record.setExpectedReturnAt(request.getExpectedReturnAt());
        record.setPurpose(request.getPurpose());
        record.setNotes(request.getNotes());
        record.setStatus(BorrowingRecord.BorrowingStatus.PENDING);

        BorrowingRecord savedRecord = borrowingRecordRepository.save(record);
        return new BorrowingResponse(savedRecord);
    }

    public BorrowingResponse approveBorrowingRequest(Long recordId, String approverUsername) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Permintaan peminjaman tidak ditemukan"));

        User approver = userRepository.findByUsername(approverUsername)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (record.getStatus() != BorrowingRecord.BorrowingStatus.PENDING) {
            throw new RuntimeException("Permintaan peminjaman sudah diproses");
        }

        Item item = record.getItem();
        if (item.getAvailableQuantity() < record.getQuantity()) {
            throw new RuntimeException("Jumlah barang yang tersedia tidak mencukupi");
        }

        // Update item quantity
        item.setAvailableQuantity(item.getAvailableQuantity() - record.getQuantity());
        itemRepository.save(item);

        // Update borrowing record
        record.setStatus(BorrowingRecord.BorrowingStatus.BORROWED);
        record.setApprovedBy(approver);
        record.setBorrowedAt(LocalDateTime.now());

        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return new BorrowingResponse(updatedRecord);
    }

    public BorrowingResponse returnItem(Long recordId, String notes) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Catatan peminjaman tidak ditemukan"));

        if (record.getStatus() != BorrowingRecord.BorrowingStatus.BORROWED) {
            throw new RuntimeException("Barang belum dipinjam atau sudah dikembalikan");
        }

        // Update item quantity
        Item item = record.getItem();
        item.setAvailableQuantity(item.getAvailableQuantity() + record.getQuantity());
        itemRepository.save(item);

        // Update borrowing record
        record.setStatus(BorrowingRecord.BorrowingStatus.RETURNED);
        record.setReturnedAt(LocalDateTime.now());
        if (notes != null) {
            record.setNotes(record.getNotes() + "\nPengembalian: " + notes);
        }

        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return new BorrowingResponse(updatedRecord);
    }

    public BorrowingResponse rejectBorrowingRequest(Long recordId, String reason) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Permintaan peminjaman tidak ditemukan"));

        if (record.getStatus() != BorrowingRecord.BorrowingStatus.PENDING) {
            throw new RuntimeException("Permintaan peminjaman sudah diproses");
        }

        record.setStatus(BorrowingRecord.BorrowingStatus.CANCELLED);
        if (reason != null) {
            record.setNotes(record.getNotes() + "\nDitolak: " + reason);
        }

        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return new BorrowingResponse(updatedRecord);
    }

    public List<BorrowingResponse> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll().stream()
                .map(BorrowingResponse::new)
                .collect(Collectors.toList());
    }

    public List<BorrowingResponse> getBorrowingRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        
        return borrowingRecordRepository.findByBorrower(user).stream()
                .map(BorrowingResponse::new)
                .collect(Collectors.toList());
    }

    public List<BorrowingResponse> getBorrowingRecordsByStatus(BorrowingRecord.BorrowingStatus status) {
        return borrowingRecordRepository.findByStatus(status).stream()
                .map(BorrowingResponse::new)
                .collect(Collectors.toList());
    }

    public BorrowingResponse getBorrowingRecordById(Long id) {
        BorrowingRecord record = borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catatan peminjaman tidak ditemukan"));
        return new BorrowingResponse(record);
    }

    public List<BorrowingResponse> getOverdueRecords() {
        return borrowingRecordRepository.findOverdueRecords(LocalDateTime.now()).stream()
                .map(BorrowingResponse::new)
                .collect(Collectors.toList());
    }

    public List<BorrowingResponse> getBorrowingRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return borrowingRecordRepository.findByBorrowedAtBetween(startDate, endDate).stream()
                .map(BorrowingResponse::new)
                .collect(Collectors.toList());
    }

    // Statistics methods
    public Long getBorrowingCountByStatus(BorrowingRecord.BorrowingStatus status) {
        return borrowingRecordRepository.countByStatus(status);
    }

    public List<Object[]> getMonthlyBorrowingStats(int year) {
        return borrowingRecordRepository.getMonthlyBorrowingStats(year);
    }
}