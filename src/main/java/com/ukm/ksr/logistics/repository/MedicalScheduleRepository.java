package com.ukm.ksr.logistics.repository;

import com.ukm.ksr.logistics.model.MedicalSchedule;
import com.ukm.ksr.logistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Long> {
    List<MedicalSchedule> findByStatus(MedicalSchedule.ScheduleStatus status);
    List<MedicalSchedule> findByCreatedBy(User createdBy);
    
    @Query("SELECT ms FROM MedicalSchedule ms WHERE ms.startDateTime BETWEEN ?1 AND ?2")
    List<MedicalSchedule> findByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT ms FROM MedicalSchedule ms JOIN ms.assignedMembers am WHERE am = ?1")
    List<MedicalSchedule> findByAssignedMember(User member);
    
    @Query("SELECT ms FROM MedicalSchedule ms JOIN ms.assignedMembers am WHERE am = ?1 AND ms.startDateTime BETWEEN ?2 AND ?3")
    List<MedicalSchedule> findByAssignedMemberAndDateRange(User member, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT COUNT(ms) FROM MedicalSchedule ms WHERE ms.status = ?1")
    Long countByStatus(MedicalSchedule.ScheduleStatus status);
    
    @Query("SELECT MONTH(ms.startDateTime), COUNT(ms) FROM MedicalSchedule ms WHERE YEAR(ms.startDateTime) = ?1 GROUP BY MONTH(ms.startDateTime)")
    List<Object[]> getMonthlyScheduleStats(int year);
}