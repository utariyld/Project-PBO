package com.ukm.ksr.logistics.repository;

import com.ukm.ksr.logistics.model.BorrowingRecord;
import com.ukm.ksr.logistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByBorrower(User borrower);
    List<BorrowingRecord> findByStatus(BorrowingRecord.BorrowingStatus status);
    List<BorrowingRecord> findByBorrowerAndStatus(User borrower, BorrowingRecord.BorrowingStatus status);
    
    @Query("SELECT br FROM BorrowingRecord br WHERE br.expectedReturnAt < ?1 AND br.status = 'BORROWED'")
    List<BorrowingRecord> findOverdueRecords(LocalDateTime currentDateTime);
    
    @Query("SELECT COUNT(br) FROM BorrowingRecord br WHERE br.status = ?1")
    Long countByStatus(BorrowingRecord.BorrowingStatus status);
    
    @Query("SELECT br FROM BorrowingRecord br WHERE br.borrowedAt BETWEEN ?1 AND ?2")
    List<BorrowingRecord> findByBorrowedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT MONTH(br.borrowedAt), COUNT(br) FROM BorrowingRecord br WHERE YEAR(br.borrowedAt) = ?1 GROUP BY MONTH(br.borrowedAt)")
    List<Object[]> getMonthlyBorrowingStats(int year);
}