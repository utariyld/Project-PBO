package com.ukm.ksr.logistics.dto;

import java.util.List;
import java.util.Map;

public class DashboardStats {
    private ItemStats itemStats;
    private BorrowingStats borrowingStats;
    private ScheduleStats scheduleStats;
    private UserStats userStats;

    // Constructors
    public DashboardStats() {}

    public DashboardStats(ItemStats itemStats, BorrowingStats borrowingStats, 
                         ScheduleStats scheduleStats, UserStats userStats) {
        this.itemStats = itemStats;
        this.borrowingStats = borrowingStats;
        this.scheduleStats = scheduleStats;
        this.userStats = userStats;
    }

    // Inner classes for different stat categories
    public static class ItemStats {
        private Long totalItems;
        private Long availableItems;
        private Long borrowedItems;
        private Long damagedItems;
        private Long lostItems;
        private List<CategoryStat> itemsByCategory;
        private List<StatusStat> itemsByStatus;

        // Constructors, getters and setters
        public ItemStats() {}

        public Long getTotalItems() { return totalItems; }
        public void setTotalItems(Long totalItems) { this.totalItems = totalItems; }

        public Long getAvailableItems() { return availableItems; }
        public void setAvailableItems(Long availableItems) { this.availableItems = availableItems; }

        public Long getBorrowedItems() { return borrowedItems; }
        public void setBorrowedItems(Long borrowedItems) { this.borrowedItems = borrowedItems; }

        public Long getDamagedItems() { return damagedItems; }
        public void setDamagedItems(Long damagedItems) { this.damagedItems = damagedItems; }

        public Long getLostItems() { return lostItems; }
        public void setLostItems(Long lostItems) { this.lostItems = lostItems; }

        public List<CategoryStat> getItemsByCategory() { return itemsByCategory; }
        public void setItemsByCategory(List<CategoryStat> itemsByCategory) { this.itemsByCategory = itemsByCategory; }

        public List<StatusStat> getItemsByStatus() { return itemsByStatus; }
        public void setItemsByStatus(List<StatusStat> itemsByStatus) { this.itemsByStatus = itemsByStatus; }
    }

    public static class BorrowingStats {
        private Long totalBorrowings;
        private Long pendingBorrowings;
        private Long activeBorrowings;
        private Long returnedBorrowings;
        private Long overdueBorrowings;
        private List<MonthlyStat> monthlyBorrowings;

        // Constructors, getters and setters
        public BorrowingStats() {}

        public Long getTotalBorrowings() { return totalBorrowings; }
        public void setTotalBorrowings(Long totalBorrowings) { this.totalBorrowings = totalBorrowings; }

        public Long getPendingBorrowings() { return pendingBorrowings; }
        public void setPendingBorrowings(Long pendingBorrowings) { this.pendingBorrowings = pendingBorrowings; }

        public Long getActiveBorrowings() { return activeBorrowings; }
        public void setActiveBorrowings(Long activeBorrowings) { this.activeBorrowings = activeBorrowings; }

        public Long getReturnedBorrowings() { return returnedBorrowings; }
        public void setReturnedBorrowings(Long returnedBorrowings) { this.returnedBorrowings = returnedBorrowings; }

        public Long getOverdueBorrowings() { return overdueBorrowings; }
        public void setOverdueBorrowings(Long overdueBorrowings) { this.overdueBorrowings = overdueBorrowings; }

        public List<MonthlyStat> getMonthlyBorrowings() { return monthlyBorrowings; }
        public void setMonthlyBorrowings(List<MonthlyStat> monthlyBorrowings) { this.monthlyBorrowings = monthlyBorrowings; }
    }

    public static class ScheduleStats {
        private Long totalSchedules;
        private Long scheduledSchedules;
        private Long inProgressSchedules;
        private Long completedSchedules;
        private Long cancelledSchedules;
        private List<MonthlyStat> monthlySchedules;

        // Constructors, getters and setters
        public ScheduleStats() {}

        public Long getTotalSchedules() { return totalSchedules; }
        public void setTotalSchedules(Long totalSchedules) { this.totalSchedules = totalSchedules; }

        public Long getScheduledSchedules() { return scheduledSchedules; }
        public void setScheduledSchedules(Long scheduledSchedules) { this.scheduledSchedules = scheduledSchedules; }

        public Long getInProgressSchedules() { return inProgressSchedules; }
        public void setInProgressSchedules(Long inProgressSchedules) { this.inProgressSchedules = inProgressSchedules; }

        public Long getCompletedSchedules() { return completedSchedules; }
        public void setCompletedSchedules(Long completedSchedules) { this.completedSchedules = completedSchedules; }

        public Long getCancelledSchedules() { return cancelledSchedules; }
        public void setCancelledSchedules(Long cancelledSchedules) { this.cancelledSchedules = cancelledSchedules; }

        public List<MonthlyStat> getMonthlySchedules() { return monthlySchedules; }
        public void setMonthlySchedules(List<MonthlyStat> monthlySchedules) { this.monthlySchedules = monthlySchedules; }
    }

    public static class UserStats {
        private Long totalUsers;
        private Long adminUsers;
        private Long memberUsers;
        private Long logisticsStaffUsers;

        // Constructors, getters and setters
        public UserStats() {}

        public Long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

        public Long getAdminUsers() { return adminUsers; }
        public void setAdminUsers(Long adminUsers) { this.adminUsers = adminUsers; }

        public Long getMemberUsers() { return memberUsers; }
        public void setMemberUsers(Long memberUsers) { this.memberUsers = memberUsers; }

        public Long getLogisticsStaffUsers() { return logisticsStaffUsers; }
        public void setLogisticsStaffUsers(Long logisticsStaffUsers) { this.logisticsStaffUsers = logisticsStaffUsers; }
    }

    public static class CategoryStat {
        private String category;
        private String categoryDisplayName;
        private Long count;

        public CategoryStat() {}

        public CategoryStat(String category, String categoryDisplayName, Long count) {
            this.category = category;
            this.categoryDisplayName = categoryDisplayName;
            this.count = count;
        }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getCategoryDisplayName() { return categoryDisplayName; }
        public void setCategoryDisplayName(String categoryDisplayName) { this.categoryDisplayName = categoryDisplayName; }

        public Long getCount() { return count; }
        public void setCount(Long count) { this.count = count; }
    }

    public static class StatusStat {
        private String status;
        private String statusDisplayName;
        private Long count;

        public StatusStat() {}

        public StatusStat(String status, String statusDisplayName, Long count) {
            this.status = status;
            this.statusDisplayName = statusDisplayName;
            this.count = count;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getStatusDisplayName() { return statusDisplayName; }
        public void setStatusDisplayName(String statusDisplayName) { this.statusDisplayName = statusDisplayName; }

        public Long getCount() { return count; }
        public void setCount(Long count) { this.count = count; }
    }

    public static class MonthlyStat {
        private Integer month;
        private String monthName;
        private Long count;

        public MonthlyStat() {}

        public MonthlyStat(Integer month, String monthName, Long count) {
            this.month = month;
            this.monthName = monthName;
            this.count = count;
        }

        public Integer getMonth() { return month; }
        public void setMonth(Integer month) { this.month = month; }

        public String getMonthName() { return monthName; }
        public void setMonthName(String monthName) { this.monthName = monthName; }

        public Long getCount() { return count; }
        public void setCount(Long count) { this.count = count; }
    }

    // Main class getters and setters
    public ItemStats getItemStats() { return itemStats; }
    public void setItemStats(ItemStats itemStats) { this.itemStats = itemStats; }

    public BorrowingStats getBorrowingStats() { return borrowingStats; }
    public void setBorrowingStats(BorrowingStats borrowingStats) { this.borrowingStats = borrowingStats; }

    public ScheduleStats getScheduleStats() { return scheduleStats; }
    public void setScheduleStats(ScheduleStats scheduleStats) { this.scheduleStats = scheduleStats; }

    public UserStats getUserStats() { return userStats; }
    public void setUserStats(UserStats userStats) { this.userStats = userStats; }
}