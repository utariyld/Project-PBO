package com.literanusa.view;

import com.literanusa.controller.AuthController;
import com.literanusa.controller.BookController;
import com.literanusa.dao.LoanDAO;
import com.literanusa.factory.DAOFactory;
import com.literanusa.model.Book;
import com.literanusa.model.Loan;
import com.literanusa.model.User;
import com.literanusa.util.ImageUtils;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserProfileView extends JFrame {
    private User currentUser;
    private AuthController authController;
    private BookController bookController;
    private LoanDAO loanDAO;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JTable loanHistoryTable;
    private DefaultTableModel loanHistoryModel;
    private JLabel profilePictureLabel;
    private JTabbedPane tabbedPane;

    // Modern Color Scheme - Same as Dashboard
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    private final Color WHITE = Color.WHITE;
    private final Color DARK_TEXT = new Color(33, 37, 41);
    private final Color LIGHT_TEXT = new Color(108, 117, 125);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private final Color WARNING_ORANGE = new Color(255, 193, 7);
    private final Color DANGER_RED = new Color(220, 53, 69);

    public UserProfileView(User user) {
        this.currentUser = user;
        this.authController = new AuthController();
        this.bookController = new BookController();
        this.loanDAO = DAOFactory.getInstance().getLoanDAO();
        initializeComponents();
        loadUserData();
        loadLoanHistory();
    }

    private void initializeComponents() {
        setTitle("Profil Pengguna - " + currentUser.getDisplayName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setBackground(WHITE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);

        // Header
        JPanel headerPanel = createModernHeader();

        // Tabbed content
        tabbedPane = createModernTabbedPane();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createModernHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_TEAL);
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Left side - Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(PRIMARY_TEAL);

        JLabel logoLabel = new JLabel("👤");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        logoLabel.setForeground(WHITE);

        JLabel titleLabel = new JLabel("Profil Pengguna - " + currentUser.getDisplayName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);

        // Right side - Close button
        JButton closeButton = new JButton("✕ Tutup");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeButton.setBackground(DANGER_RED);
        closeButton.setForeground(WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JTabbedPane createModernTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(WHITE);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Custom tab styling
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                if (isSelected) {
                    g2d.setColor(PRIMARY_TEAL);
                } else {
                    g2d.setColor(LIGHT_GRAY);
                }
                g2d.fillRect(x, y, w, h);
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                     int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                g.setColor(isSelected ? WHITE : DARK_TEXT);
                super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
            }
        });

        // Profile tab
        JPanel profilePanel = createProfilePanel();
        tabbedPane.addTab("👤 Profil Saya", profilePanel);

        // Security tab
        JPanel securityPanel = createSecurityPanel();
        tabbedPane.addTab("🔒 Keamanan", securityPanel);

        // History tab
        JPanel historyPanel = createHistoryPanel();
        tabbedPane.addTab("📚 Riwayat Peminjaman", historyPanel);

        // Statistics tab with charts
        JPanel statsPanel = createStatisticsPanel();
        tabbedPane.addTab("📊 Statistik", statsPanel);

        return tabbedPane;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Profile picture section
        JPanel pictureSection = createProfilePictureSection();

        // Form section
        JPanel formSection = createProfileFormSection();

        panel.add(pictureSection, BorderLayout.NORTH);
        panel.add(formSection, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProfilePictureSection() {
        JPanel section = new JPanel(new FlowLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Profile picture
        profilePictureLabel = new JLabel();
        profilePictureLabel.setPreferredSize(new Dimension(120, 120));
        profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePictureLabel.setVerticalAlignment(SwingConstants.CENTER);
        profilePictureLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_TEAL, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Load profile picture or show default
        ImageIcon profileIcon = ImageUtils.loadImageIcon(currentUser.getProfilePicturePath(), 110, 110);
        if (profileIcon != null) {
            profilePictureLabel.setIcon(profileIcon);
        } else {
            profilePictureLabel.setText("👤");
            profilePictureLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            profilePictureLabel.setForeground(PRIMARY_TEAL);
            profilePictureLabel.setOpaque(true);
            profilePictureLabel.setBackground(LIGHT_GRAY);
        }

        // Change photo button
        JButton changePhotoButton = new JButton("📷 Ubah Foto");
        changePhotoButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        changePhotoButton.setBackground(SECONDARY_TEAL);
        changePhotoButton.setForeground(WHITE);
        changePhotoButton.setBorderPainted(false);
        changePhotoButton.setFocusPainted(false);
        changePhotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePhotoButton.addActionListener(e -> changeProfilePicture());

        JPanel photoContainer = new JPanel();
        photoContainer.setLayout(new BoxLayout(photoContainer, BoxLayout.Y_AXIS));
        photoContainer.setBackground(WHITE);

        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        photoContainer.add(profilePictureLabel);
        photoContainer.add(Box.createVerticalStrut(15));
        photoContainer.add(changePhotoButton);

        section.add(photoContainer);
        return section;
    }

    private JPanel createProfileFormSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel sectionTitle = new JLabel("ℹ️ Informasi Pribadi");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setForeground(DARK_TEXT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Form fields
        usernameField = createStyledTextField(20);
        usernameField.setEditable(false);
        usernameField.setBackground(LIGHT_GRAY);

        emailField = createStyledTextField(20);
        fullNameField = createStyledTextField(20);
        phoneField = createStyledTextField(20);

        addressArea = new JTextArea(3, 20);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);

        // Add form fields
        addFormField(formPanel, gbc, "Username:", usernameField, 0);
        addFormField(formPanel, gbc, "Email:", emailField, 1);
        addFormField(formPanel, gbc, "Nama Lengkap:", fullNameField, 2);
        addFormField(formPanel, gbc, "Telepon:", phoneField, 3);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createStyledLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(addressArea), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(WHITE);

        JButton updateButton = createStyledButton("💾 Update Profil", SUCCESS_GREEN);
        updateButton.addActionListener(e -> updateProfile());

        JButton resetButton = createStyledButton("🔄 Reset", WARNING_ORANGE);
        resetButton.addActionListener(e -> loadUserData());

        buttonPanel.add(updateButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(formPanel, BorderLayout.CENTER);

        return section;
    }

    private JPanel createSecurityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Password change section
        JPanel passwordSection = new JPanel(new BorderLayout());
        passwordSection.setBackground(WHITE);
        passwordSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel sectionTitle = new JLabel("🔐 Ubah Password");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setForeground(DARK_TEXT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        currentPasswordField = createStyledPasswordField(20);
        newPasswordField = createStyledPasswordField(20);
        confirmPasswordField = createStyledPasswordField(20);

        addFormField(formPanel, gbc, "Password Saat Ini:", currentPasswordField, 0);
        addFormField(formPanel, gbc, "Password Baru:", newPasswordField, 1);
        addFormField(formPanel, gbc, "Konfirmasi Password:", confirmPasswordField, 2);

        JButton changePasswordButton = createStyledButton("🔐 Ubah Password", PRIMARY_TEAL);
        changePasswordButton.addActionListener(e -> changePassword());

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(changePasswordButton, gbc);

        passwordSection.add(sectionTitle, BorderLayout.NORTH);
        passwordSection.add(formPanel, BorderLayout.CENTER);

        // Security tips section
        JPanel tipsSection = createSecurityTipsSection();

        panel.add(passwordSection, BorderLayout.NORTH);
        panel.add(tipsSection, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSecurityTipsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel sectionTitle = new JLabel("💡 Tips Keamanan");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setForeground(DARK_TEXT);

        JTextArea tipsArea = new JTextArea(
                "TIPS KEAMANAN AKUN:\n\n" +
                        "• Gunakan password yang kuat (minimal 8 karakter)\n" +
                        "• Kombinasikan huruf besar, kecil, angka, dan simbol\n" +
                        "• Jangan gunakan informasi pribadi dalam password\n" +
                        "• Ubah password secara berkala\n" +
                        "• Jangan bagikan password kepada orang lain\n" +
                        "• Logout setelah selesai menggunakan sistem\n\n" +
                        "Jika Anda mencurigai akun telah dikompromikan,\n" +
                        "segera hubungi administrator sistem."
        );
        tipsArea.setEditable(false);
        tipsArea.setBackground(LIGHT_GRAY);
        tipsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tipsArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(tipsArea, BorderLayout.CENTER);

        return section;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Filter panel
        JPanel filterPanel = createHistoryFilterPanel();

        // Table panel
        JPanel tablePanel = createHistoryTablePanel();

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHistoryFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel filterLabel = new JLabel("🔍 Filter Status:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterLabel.setForeground(DARK_TEXT);

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"Semua", "ACTIVE", "RETURNED", "OVERDUE"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusFilter.setBackground(WHITE);
        statusFilter.addActionListener(e -> filterLoanHistory((String) statusFilter.getSelectedItem()));

        JButton refreshButton = createStyledButton("🔄 Refresh", SECONDARY_TEAL);
        refreshButton.addActionListener(e -> loadLoanHistory());

        panel.add(filterLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(statusFilter);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createHistoryTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JLabel tableTitle = new JLabel("📚 Riwayat Peminjaman");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(DARK_TEXT);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Create table
        String[] columns = {"ID", "Judul Buku", "Penulis", "Tanggal Pinjam", "Tanggal Kembali", "Status", "Denda"};
        loanHistoryModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loanHistoryTable = new JTable(loanHistoryModel);
        loanHistoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loanHistoryTable.setRowHeight(30);
        loanHistoryTable.setBackground(WHITE);
        loanHistoryTable.setGridColor(new Color(222, 226, 230));
        loanHistoryTable.setSelectionBackground(LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(loanHistoryTable);
        scrollPane.setBorder(null);

        panel.add(tableTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Statistics cards
        JPanel statsCards = createStatisticsCards();

        // Chart panel
        JPanel chartPanel = createChartPanel();

        panel.add(statsCards, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatisticsCards() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Calculate statistics
        List<Loan> userLoans = loanDAO.getLoansByUserId(currentUser.getId());

        int totalLoans = userLoans.size();
        int activeLoans = (int) userLoans.stream().filter(l -> l.getStatus() == Loan.Status.ACTIVE).count();
        int returnedBooks = (int) userLoans.stream().filter(l -> l.getStatus() == Loan.Status.RETURNED).count();
        int overdueBooks = (int) userLoans.stream().filter(l -> l.getStatus() == Loan.Status.OVERDUE).count();

        panel.add(createStatCard("📚", "Total Peminjaman", String.valueOf(totalLoans), PRIMARY_TEAL));
        panel.add(createStatCard("📖", "Sedang Dipinjam", String.valueOf(activeLoans), WARNING_ORANGE));
        panel.add(createStatCard("✅", "Sudah Dikembalikan", String.valueOf(returnedBooks), SUCCESS_GREEN));
        panel.add(createStatCard("⚠️", "Terlambat", String.valueOf(overdueBooks), DANGER_RED));

        return panel;
    }

    private JPanel createStatCard(String icon, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(LIGHT_TEXT);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(WHITE);

        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(valueLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(titleLabel);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel chartTitle = new JLabel("📊 Statistik Peminjaman per Bulan");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setForeground(DARK_TEXT);
        chartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Create custom chart panel
        JPanel chartArea = new BarChartPanel();
        chartArea.setPreferredSize(new Dimension(0, 300));

        panel.add(chartTitle, BorderLayout.NORTH);
        panel.add(chartArea, BorderLayout.CENTER);

        return panel;
    }

    // Custom Bar Chart Panel
    private class BarChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get loan data by month
            List<Loan> userLoans = loanDAO.getLoansByUserId(currentUser.getId());
            Map<String, Long> loansByMonth = userLoans.stream()
                    .collect(Collectors.groupingBy(
                            loan -> loan.getLoanDate().format(DateTimeFormatter.ofPattern("MMM yyyy")),
                            Collectors.counting()
                    ));

            if (loansByMonth.isEmpty()) {
                // Draw "No data" message
                g2d.setColor(LIGHT_TEXT);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                FontMetrics fm = g2d.getFontMetrics();
                String message = "Belum ada data peminjaman";
                int x = (getWidth() - fm.stringWidth(message)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(message, x, y);
                return;
            }

            // Chart dimensions
            int margin = 50;
            int chartWidth = getWidth() - 2 * margin;
            int chartHeight = getHeight() - 2 * margin;
            int barWidth = chartWidth / Math.max(loansByMonth.size(), 1) - 10;

            // Find max value for scaling
            long maxValue = loansByMonth.values().stream().mapToLong(Long::longValue).max().orElse(1);

            // Draw axes
            g2d.setColor(DARK_TEXT);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(margin, margin, margin, margin + chartHeight); // Y-axis
            g2d.drawLine(margin, margin + chartHeight, margin + chartWidth, margin + chartHeight); // X-axis

            // Draw bars
            int x = margin + 10;
            int index = 0;
            Color[] barColors = {PRIMARY_TEAL, SECONDARY_TEAL, SUCCESS_GREEN, WARNING_ORANGE};

            for (Map.Entry<String, Long> entry : loansByMonth.entrySet()) {
                String month = entry.getKey();
                long count = entry.getValue();

                int barHeight = (int) ((double) count / maxValue * (chartHeight - 20));
                int barY = margin + chartHeight - barHeight;

                // Draw bar
                g2d.setColor(barColors[index % barColors.length]);
                g2d.fillRect(x, barY, barWidth, barHeight);

                // Draw value on top of bar
                g2d.setColor(DARK_TEXT);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2d.getFontMetrics();
                String valueStr = String.valueOf(count);
                int valueX = x + (barWidth - fm.stringWidth(valueStr)) / 2;
                g2d.drawString(valueStr, valueX, barY - 5);

                // Draw month label
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                fm = g2d.getFontMetrics();
                int labelX = x + (barWidth - fm.stringWidth(month)) / 2;
                g2d.drawString(month, labelX, margin + chartHeight + 15);

                x += barWidth + 10;
                index++;
            }

            // Draw Y-axis labels
            g2d.setColor(LIGHT_TEXT);
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            for (int i = 0; i <= 5; i++) {
                int value = (int) (maxValue * i / 5);
                int y = margin + chartHeight - (chartHeight * i / 5);
                g2d.drawString(String.valueOf(value), margin - 30, y + 5);

                // Draw grid lines
                g2d.setColor(new Color(222, 226, 230));
                g2d.drawLine(margin, y, margin + chartWidth, y);
                g2d.setColor(LIGHT_TEXT);
            }
        }
    }

    // Helper methods for creating styled components
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(DARK_TEXT);
        return label;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = backgroundColor;

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createStyledLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Data loading and functionality methods
    private void loadUserData() {
        usernameField.setText(currentUser.getUsername());
        emailField.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");
        fullNameField.setText(currentUser.getFullName() != null ? currentUser.getFullName() : "");
        phoneField.setText(currentUser.getPhone() != null ? currentUser.getPhone() : "");
        addressArea.setText(currentUser.getAddress() != null ? currentUser.getAddress() : "");
    }

    private void loadLoanHistory() {
        loanHistoryModel.setRowCount(0);
        List<Loan> loans = loanDAO.getLoansByUserId(currentUser.getId());

        for (Loan loan : loans) {
            Book book = bookController.getBookById(loan.getBookId());
            Object[] row = {
                    loan.getId(),
                    book != null ? book.getTitle() : "Unknown",
                    book != null ? book.getAuthor() : "Unknown",
                    loan.getLoanDate(),
                    loan.getReturnDate() != null ? loan.getReturnDate() : "-",
                    loan.getStatus(),
                    "Rp 0" // You can implement fine calculation here
            };
            loanHistoryModel.addRow(row);
        }
    }

    private void filterLoanHistory(String status) {
        if ("Semua".equals(status)) {
            loadLoanHistory();
            return;
        }

        loanHistoryModel.setRowCount(0);
        List<Loan> loans = loanDAO.getLoansByUserId(currentUser.getId());

        for (Loan loan : loans) {
            if (loan.getStatus().toString().equals(status)) {
                Book book = bookController.getBookById(loan.getBookId());
                Object[] row = {
                        loan.getId(),
                        book != null ? book.getTitle() : "Unknown",
                        book != null ? book.getAuthor() : "Unknown",
                        loan.getLoanDate(),
                        loan.getReturnDate() != null ? loan.getReturnDate() : "-",
                        loan.getStatus(),
                        "Rp 0"
                };
                loanHistoryModel.addRow(row);
            }
        }
    }

    private void updateProfile() {
        String email = emailField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressArea.getText().trim();

        if (email.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Email dan Nama Lengkap harus diisi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Format email tidak valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update user object
        currentUser.setEmail(email);
        currentUser.setFullName(fullName);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);

        // Update database
        if (authController.updateProfile(currentUser)) {
            JOptionPane.showMessageDialog(this,
                    "Profil berhasil diupdate!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Update window title
            setTitle("Profil Pengguna - " + currentUser.getDisplayName());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengupdate profil!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field password harus diisi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Password baru dan konfirmasi tidak cocok!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "Password baru minimal 6 karakter!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify current password
        if (!currentUser.getPassword().equals(currentPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Password saat ini salah!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update password
        if (authController.changePassword(currentUser.getId(), newPassword)) {
            currentUser.setPassword(newPassword);

            // Clear password fields
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");

            JOptionPane.showMessageDialog(this,
                    "Password berhasil diubah!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengubah password!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
        fileChooser.setDialogTitle("Pilih Foto Profil");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // In a real application, you would upload the file to a server
                // For now, we'll just show a success message
                String fileName = "profile_" + currentUser.getId() + "_" + selectedFile.getName();

                // Update profile picture in database
                if (authController.updateProfilePicture(currentUser.getId(), fileName)) {
                    currentUser.setProfilePicture(fileName);

                    // Update the profile picture display
                    ImageIcon newIcon = ImageUtils.loadImageIcon(selectedFile.getAbsolutePath(), 110, 110);
                    if (newIcon != null) {
                        profilePictureLabel.setIcon(newIcon);
                        profilePictureLabel.setText("");
                    }

                    JOptionPane.showMessageDialog(this,
                            "Foto profil berhasil diupdate!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Gagal mengupdate foto profil!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error memproses file: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
