package com.literanusa.view;

import com.literanusa.controller.BookController;
import com.literanusa.dao.LoanDAO;
import com.literanusa.factory.DAOFactory;
import com.literanusa.model.Book;
import com.literanusa.model.Loan;
import com.literanusa.model.User;
import com.literanusa.util.ImageUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDashboardView extends JFrame {
    private User currentUser;
    private BookController bookController;
    private LoanDAO loanDAO;
    private JTabbedPane tabbedPane;
    private JTable booksTable;
    private JTable loansTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel loansTableModel;

    // Modern Color Scheme
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    private final Color WHITE = Color.WHITE;
    private final Color DARK_TEXT = new Color(33, 37, 41);
    private final Color LIGHT_TEXT = new Color(108, 117, 125);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private final Color WARNING_ORANGE = new Color(255, 193, 7);
    private final Color DANGER_RED = new Color(220, 53, 69);

    public AdminDashboardView(User user) {
        this.currentUser = user;
        this.bookController = new BookController();
        this.loanDAO = DAOFactory.getInstance().getLoanDAO();
        initializeComponents();
        loadBooks();
        loadAllLoans();
    }

    private void initializeComponents() {
        setTitle("Admin Dashboard - LiteraNusa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setBackground(WHITE);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);

        // Top Navigation Bar
        JPanel navBar = createAdminNavBar();

        // Dashboard Content
        JScrollPane contentScrollPane = createAdminDashboard();

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createAdminNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(WHITE);
        navBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        navBar.setPreferredSize(new Dimension(0, 70));

        // Left side - Logo
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(WHITE);

        JLabel logoLabel = new JLabel("🛡️ LiteraNusa Admin");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(PRIMARY_TEAL);
        leftPanel.add(logoLabel);

        // Right side - Admin menu
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(WHITE);

        JButton dashboardBtn = createNavButton("Dashboard", true);
        JButton booksBtn = createNavButton("Kelola Buku", false);
        JButton loansBtn = createNavButton("Peminjaman", false);
        JButton usersBtn = createNavButton("Pengguna", false);

        JLabel adminGreeting = new JLabel("Admin: " + currentUser.getFullName());
        adminGreeting.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminGreeting.setForeground(LIGHT_TEXT);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setBackground(DANGER_RED);
        logoutBtn.setForeground(WHITE);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        rightPanel.add(dashboardBtn);
        rightPanel.add(booksBtn);
        rightPanel.add(loansBtn);
        rightPanel.add(usersBtn);
        rightPanel.add(adminGreeting);
        rightPanel.add(logoutBtn);

        navBar.add(leftPanel, BorderLayout.WEST);
        navBar.add(rightPanel, BorderLayout.EAST);

        return navBar;
    }

    private JButton createNavButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (active) {
            button.setForeground(PRIMARY_TEAL);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        } else {
            button.setForeground(LIGHT_TEXT);
        }

        return button;
    }

    private JScrollPane createAdminDashboard() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(LIGHT_GRAY);

        // Statistics Cards Section
        JPanel statsSection = createStatsSection();
        dashboardPanel.add(statsSection);

        // Recent Activities Section
        JPanel activitiesSection = createActivitiesSection();
        dashboardPanel.add(activitiesSection);

        // Quick Actions Section
        JPanel quickActionsSection = createQuickActionsSection();
        dashboardPanel.add(quickActionsSection);

        JScrollPane scrollPane = new JScrollPane(dashboardPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JPanel createStatsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel sectionTitle = new JLabel("📊 Statistik Perpustakaan");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(DARK_TEXT);

        // Stats cards grid
        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 20, 0));
        statsGrid.setBackground(WHITE);
        statsGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Calculate statistics
        List<Book> allBooks = bookController.getAllBooks();
        List<Loan> allLoans = loanDAO.getAllLoans();

        int totalBooks = allBooks.size();
        int availableBooks = allBooks.stream().mapToInt(Book::getAvailableCopies).sum();
        int activeLoans = (int) allLoans.stream().filter(l -> l.getStatus() == Loan.Status.ACTIVE).count();
        int overdueLoans = (int) allLoans.stream().filter(l -> l.getStatus() == Loan.Status.OVERDUE).count();

        statsGrid.add(createStatCard("📚", "Total Buku", String.valueOf(totalBooks), PRIMARY_TEAL));
        statsGrid.add(createStatCard("✅", "Tersedia", String.valueOf(availableBooks), SUCCESS_GREEN));
        statsGrid.add(createStatCard("📖", "Dipinjam", String.valueOf(activeLoans), WARNING_ORANGE));
        statsGrid.add(createStatCard("⚠️", "Terlambat", String.valueOf(overdueLoans), DANGER_RED));

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(statsGrid, BorderLayout.CENTER);

        return section;
    }

    private JPanel createStatCard(String icon, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(WHITE);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconPanel.add(iconLabel);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(LIGHT_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(valueLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(titleLabel);

        card.add(iconPanel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createActivitiesSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel sectionTitle = new JLabel("📋 Aktivitas Terbaru");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(DARK_TEXT);

        // Recent loans table
        String[] columns = {"ID", "Pengguna", "Buku", "Tanggal", "Status"};
        loansTableModel = new DefaultTableModel(columns, 0);
        loansTable = new JTable(loansTableModel);
        loansTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loansTable.setRowHeight(30);
        loansTable.setBackground(WHITE);
        loansTable.setGridColor(new Color(222, 226, 230));

        JScrollPane tableScrollPane = new JScrollPane(loansTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        tableScrollPane.setPreferredSize(new Dimension(0, 300));

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(tableScrollPane, BorderLayout.CENTER);

        return section;
    }

    private JPanel createQuickActionsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(LIGHT_GRAY);
        section.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 30));

        JLabel sectionTitle = new JLabel("⚡ Aksi Cepat");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(DARK_TEXT);

        JPanel actionsGrid = new JPanel(new GridLayout(1, 4, 20, 0));
        actionsGrid.setBackground(LIGHT_GRAY);
        actionsGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        actionsGrid.add(createActionButton("➕", "Tambah Buku", PRIMARY_TEAL, e -> showAddBookDialog()));
        actionsGrid.add(createActionButton("👥", "Kelola Pengguna", SUCCESS_GREEN, e -> showUsersDialog()));
        actionsGrid.add(createActionButton("📊", "Laporan", WARNING_ORANGE, e -> showReportsDialog()));
        actionsGrid.add(createActionButton("⚙️", "Pengaturan", LIGHT_TEXT, e -> showSettingsDialog()));

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(actionsGrid, BorderLayout.CENTER);

        return section;
    }

    private JButton createActionButton(String icon, String text, Color color, ActionListener action) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));

        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        textLabel.setForeground(color);

        button.add(iconLabel, BorderLayout.CENTER);
        button.add(textLabel, BorderLayout.SOUTH);

        button.addActionListener(action);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(WHITE);
            }
        });

        return button;
    }

    private void loadBooks() {
        booksTableModel.setRowCount(0);
        List<Book> books = bookController.getAllBooks();

        for (Book book : books) {
            Object[] row = {
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getGenre(),
                    String.format("%.1f", book.getRating()),
                    book.getAvailableCopies(),
                    book.getTotalCopies()
            };
            booksTableModel.addRow(row);
        }
    }

    private void loadAllLoans() {
        if (loansTableModel == null) return;

        loansTableModel.setRowCount(0);
        List<Loan> loans = loanDAO.getAllLoans().stream()
                .limit(10) // Show only recent 10 loans
                .collect(Collectors.toList());

        for (Loan loan : loans) {
            Book book = bookController.getBookById(loan.getBookId());
            Object[] row = {
                    loan.getId(),
                    "User " + loan.getUserId(), // You might want to get actual username
                    book != null ? book.getTitle() : "Unknown",
                    loan.getLoanDate(),
                    loan.getStatus()
            };
            loansTableModel.addRow(row);
        }
    }

    // Dialog methods
    private void showAddBookDialog() {
        JOptionPane.showMessageDialog(this, "Fitur Tambah Buku akan segera tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUsersDialog() {
        JOptionPane.showMessageDialog(this, "Fitur Kelola Pengguna akan segera tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReportsDialog() {
        JOptionPane.showMessageDialog(this, "Fitur Laporan akan segera tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSettingsDialog() {
        JOptionPane.showMessageDialog(this, "Fitur Pengaturan akan segera tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
