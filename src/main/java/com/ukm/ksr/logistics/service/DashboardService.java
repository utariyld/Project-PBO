package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.DashboardStats;
import com.ukm.ksr.logistics.model.BorrowingRecord;
import com.ukm.ksr.logistics.model.Item;
import com.ukm.ksr.logistics.model.MedicalSchedule;
import com.ukm.ksr.logistics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private MedicalScheduleService medicalScheduleService;

    @Autowired
    private UserService userService;

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        // Get item statistics
        stats.setItemStats(getItemStats());
        
        // Get borrowing statistics
        stats.setBorrowingStats(getBorrowingStats());
        
        // Get schedule statistics
        stats.setScheduleStats(getScheduleStats());
        
        // Get user statistics
        stats.setUserStats(getUserStats());
        
        return stats;
    }

    private DashboardStats.ItemStats getItemStats() {
        DashboardStats.ItemStats itemStats = new DashboardStats.ItemStats();
        
        // Get counts by status
        itemStats.setTotalItems(itemService.getAllItems().stream().mapToLong(i -> 1L).sum());
        itemStats.setAvailableItems(itemService.getItemCountByStatus(Item.ItemStatus.AVAILABLE));
        itemStats.setBorrowedItems(itemService.getItemCountByStatus(Item.ItemStatus.BORROWED));
        itemStats.setDamagedItems(itemService.getItemCountByStatus(Item.ItemStatus.DAMAGED));
        itemStats.setLostItems(itemService.getItemCountByStatus(Item.ItemStatus.LOST));
        
        // Get items by category
        List<DashboardStats.CategoryStat> categoryStats = new ArrayList<>();
        List<Object[]> categoryData = itemService.getItemStatsByCategory();
        for (Object[] data : categoryData) {
            Item.ItemCategory category = (Item.ItemCategory) data[0];
            Long count = (Long) data[1];
            categoryStats.add(new DashboardStats.CategoryStat(
                category.name(), 
                category.getDisplayName(), 
                count
            ));
        }
        itemStats.setItemsByCategory(categoryStats);
        
        // Get items by status
        List<DashboardStats.StatusStat> statusStats = new ArrayList<>();
        List<Object[]> statusData = itemService.getItemStatsByStatus();
        for (Object[] data : statusData) {
            Item.ItemStatus status = (Item.ItemStatus) data[0];
            Long count = (Long) data[1];
            statusStats.add(new DashboardStats.StatusStat(
                status.name(), 
                status.getDisplayName(), 
                count
            ));
        }
        itemStats.setItemsByStatus(statusStats);
        
        return itemStats;
    }

    private DashboardStats.BorrowingStats getBorrowingStats() {
        DashboardStats.BorrowingStats borrowingStats = new DashboardStats.BorrowingStats();
        
        // Get counts by status
        borrowingStats.setTotalBorrowings(borrowingService.getAllBorrowingRecords().stream().mapToLong(i -> 1L).sum());
        borrowingStats.setPendingBorrowings(borrowingService.getBorrowingCountByStatus(BorrowingRecord.BorrowingStatus.PENDING));
        borrowingStats.setActiveBorrowings(borrowingService.getBorrowingCountByStatus(BorrowingRecord.BorrowingStatus.BORROWED));
        borrowingStats.setReturnedBorrowings(borrowingService.getBorrowingCountByStatus(BorrowingRecord.BorrowingStatus.RETURNED));
        borrowingStats.setOverdueBorrowings(borrowingService.getOverdueRecords().stream().mapToLong(i -> 1L).sum());
        
        // Get monthly borrowing statistics
        List<DashboardStats.MonthlyStat> monthlyStats = new ArrayList<>();
        int currentYear = LocalDateTime.now().getYear();
        List<Object[]> monthlyData = borrowingService.getMonthlyBorrowingStats(currentYear);
        
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for (Object[] data : monthlyData) {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            monthlyStats.add(new DashboardStats.MonthlyStat(
                month, 
                monthNames[month - 1], 
                count
            ));
        }
        borrowingStats.setMonthlyBorrowings(monthlyStats);
        
        return borrowingStats;
    }

    private DashboardStats.ScheduleStats getScheduleStats() {
        DashboardStats.ScheduleStats scheduleStats = new DashboardStats.ScheduleStats();
        
        // Get counts by status
        scheduleStats.setTotalSchedules(medicalScheduleService.getAllSchedules().stream().mapToLong(i -> 1L).sum());
        scheduleStats.setScheduledSchedules(medicalScheduleService.getScheduleCountByStatus(MedicalSchedule.ScheduleStatus.SCHEDULED));
        scheduleStats.setInProgressSchedules(medicalScheduleService.getScheduleCountByStatus(MedicalSchedule.ScheduleStatus.IN_PROGRESS));
        scheduleStats.setCompletedSchedules(medicalScheduleService.getScheduleCountByStatus(MedicalSchedule.ScheduleStatus.COMPLETED));
        scheduleStats.setCancelledSchedules(medicalScheduleService.getScheduleCountByStatus(MedicalSchedule.ScheduleStatus.CANCELLED));
        
        // Get monthly schedule statistics
        List<DashboardStats.MonthlyStat> monthlyStats = new ArrayList<>();
        int currentYear = LocalDateTime.now().getYear();
        List<Object[]> monthlyData = medicalScheduleService.getMonthlyScheduleStats(currentYear);
        
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for (Object[] data : monthlyData) {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            monthlyStats.add(new DashboardStats.MonthlyStat(
                month, 
                monthNames[month - 1], 
                count
            ));
        }
        scheduleStats.setMonthlySchedules(monthlyStats);
        
        return scheduleStats;
    }

    private DashboardStats.UserStats getUserStats() {
        DashboardStats.UserStats userStats = new DashboardStats.UserStats();
        
        // Get user counts by role
        userStats.setTotalUsers(userService.getAllUsers().stream().mapToLong(i -> 1L).sum());
        userStats.setAdminUsers(userService.getUsersByRole(User.Role.ADMIN).stream().mapToLong(i -> 1L).sum());
        userStats.setMemberUsers(userService.getUsersByRole(User.Role.MEMBER).stream().mapToLong(i -> 1L).sum());
        userStats.setLogisticsStaffUsers(userService.getUsersByRole(User.Role.LOGISTICS_STAFF).stream().mapToLong(i -> 1L).sum());
        
        return userStats;
    }
}