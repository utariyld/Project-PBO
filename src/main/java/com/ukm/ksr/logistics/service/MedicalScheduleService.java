package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.MedicalScheduleRequest;
import com.ukm.ksr.logistics.dto.MedicalScheduleResponse;
import com.ukm.ksr.logistics.model.MedicalSchedule;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.repository.MedicalScheduleRepository;
import com.ukm.ksr.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalScheduleService {

    @Autowired
    private MedicalScheduleRepository medicalScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    public MedicalScheduleResponse createSchedule(String creatorUsername, MedicalScheduleRequest request) {
        User creator = userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Validate dates
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new RuntimeException("Waktu selesai tidak boleh sebelum waktu mulai");
        }

        MedicalSchedule schedule = new MedicalSchedule();
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setStartDateTime(request.getStartDateTime());
        schedule.setEndDateTime(request.getEndDateTime());
        schedule.setLocation(request.getLocation());
        schedule.setCreatedBy(creator);
        schedule.setNotes(request.getNotes());

        // Assign members if provided
        if (request.getAssignedMemberIds() != null && !request.getAssignedMemberIds().isEmpty()) {
            Set<User> assignedMembers = new HashSet<>();
            for (Long memberId : request.getAssignedMemberIds()) {
                User member = userRepository.findById(memberId)
                        .orElseThrow(() -> new RuntimeException("Anggota dengan ID " + memberId + " tidak ditemukan"));
                assignedMembers.add(member);
            }
            schedule.setAssignedMembers(assignedMembers);
        }

        MedicalSchedule savedSchedule = medicalScheduleRepository.save(schedule);
        return new MedicalScheduleResponse(savedSchedule);
    }

    public List<MedicalScheduleResponse> getAllSchedules() {
        return medicalScheduleRepository.findAll().stream()
                .map(MedicalScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public MedicalScheduleResponse getScheduleById(Long id) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));
        return new MedicalScheduleResponse(schedule);
    }

    public MedicalScheduleResponse updateSchedule(Long id, MedicalScheduleRequest request) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        // Validate dates
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new RuntimeException("Waktu selesai tidak boleh sebelum waktu mulai");
        }

        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setStartDateTime(request.getStartDateTime());
        schedule.setEndDateTime(request.getEndDateTime());
        schedule.setLocation(request.getLocation());
        schedule.setNotes(request.getNotes());

        // Update assigned members if provided
        if (request.getAssignedMemberIds() != null) {
            Set<User> assignedMembers = new HashSet<>();
            for (Long memberId : request.getAssignedMemberIds()) {
                User member = userRepository.findById(memberId)
                        .orElseThrow(() -> new RuntimeException("Anggota dengan ID " + memberId + " tidak ditemukan"));
                assignedMembers.add(member);
            }
            schedule.setAssignedMembers(assignedMembers);
        }

        MedicalSchedule updatedSchedule = medicalScheduleRepository.save(schedule);
        return new MedicalScheduleResponse(updatedSchedule);
    }

    public void deleteSchedule(Long id) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));
        medicalScheduleRepository.delete(schedule);
    }

    public MedicalScheduleResponse updateScheduleStatus(Long id, MedicalSchedule.ScheduleStatus status) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));
        schedule.setStatus(status);
        MedicalSchedule updatedSchedule = medicalScheduleRepository.save(schedule);
        return new MedicalScheduleResponse(updatedSchedule);
    }

    public List<MedicalScheduleResponse> getSchedulesByStatus(MedicalSchedule.ScheduleStatus status) {
        return medicalScheduleRepository.findByStatus(status).stream()
                .map(MedicalScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public List<MedicalScheduleResponse> getSchedulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return medicalScheduleRepository.findByStartDateTimeBetween(startDate, endDate).stream()
                .map(MedicalScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public List<MedicalScheduleResponse> getSchedulesForMember(String username) {
        User member = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        
        return medicalScheduleRepository.findByAssignedMember(member).stream()
                .map(MedicalScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public List<MedicalScheduleResponse> getSchedulesForMemberByDateRange(String username, LocalDateTime startDate, LocalDateTime endDate) {
        User member = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        
        return medicalScheduleRepository.findByAssignedMemberAndDateRange(member, startDate, endDate).stream()
                .map(MedicalScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public MedicalScheduleResponse assignMembersToSchedule(Long scheduleId, Set<Long> memberIds) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        Set<User> assignedMembers = new HashSet<>();
        for (Long memberId : memberIds) {
            User member = userRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Anggota dengan ID " + memberId + " tidak ditemukan"));
            assignedMembers.add(member);
        }

        schedule.setAssignedMembers(assignedMembers);
        MedicalSchedule updatedSchedule = medicalScheduleRepository.save(schedule);
        return new MedicalScheduleResponse(updatedSchedule);
    }

    public MedicalScheduleResponse removeMemberFromSchedule(Long scheduleId, Long memberId) {
        MedicalSchedule schedule = medicalScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Anggota tidak ditemukan"));

        schedule.getAssignedMembers().remove(member);
        MedicalSchedule updatedSchedule = medicalScheduleRepository.save(schedule);
        return new MedicalScheduleResponse(updatedSchedule);
    }

    // Statistics methods
    public Long getScheduleCountByStatus(MedicalSchedule.ScheduleStatus status) {
        return medicalScheduleRepository.countByStatus(status);
    }

    public List<Object[]> getMonthlyScheduleStats(int year) {
        return medicalScheduleRepository.getMonthlyScheduleStats(year);
    }
}