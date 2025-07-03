package com.literanusa.view;

import com.literanusa.controller.BookController;
import com.literanusa.dao.LoanDAO;
import com.literanusa.factory.DAOFactory;
import com.literanusa.model.Book;
import com.literanusa.model.Loan;
import com.literanusa.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.literanusa.util.ImageUtils;
import com.literanusa.view.UserProfileView;

public class UserDashboardView extends JFrame {
    private User currentUser;
    private BookController bookController;
    private LoanDAO loanDAO;
    private JTabbedPane tabbedPane;
    private JTextField searchField;
    private JTable booksTable;
    private JTable loansTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel loansTableModel;
    private JPanel booksCardsPanel;
    private JScrollPane cardsScrollPane;
    private List<Book> currentBooks;
    private Book selectedBook;
    private String selectedGenre = "Semua";

    // Modern Color Scheme
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    private final Color WHITE = Color.WHITE;
    private final Color DARK_TEXT = new Color(33, 37, 41);
    private final Color LIGHT_TEXT = new Color(108, 117, 125);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private final Color DANGER_RED = new Color(220, 53, 69);
    private final Color CARD_SHADOW = new Color(0, 0, 0, 10);

    public UserDashboardView(User user) {
        this.currentUser = user;
        this.bookController = new BookController();
        this.loanDAO = DAOFactory.getInstance().getLoanDAO();
        initializeComponents();
        loadBooks();
        loadUserLoans();
    }

    private void initializeComponents() {
        setTitle("Digital Library - LiteraNusa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setBackground(WHITE);

        // Create main panel with modern layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);

        // Top Navigation Bar
        JPanel navBar = createModernNavBar();

        // Main content area
        JScrollPane contentScrollPane = createMainContent();

        // Footer
        JPanel footer = createModernFooter();

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createModernNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(WHITE);
        navBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        navBar.setPreferredSize(new Dimension(0, 70));

        // Left side - Logo dengan gambar
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(WHITE);

        // Load logo image
        ImageIcon logoIcon = ImageUtils.loadImageIcon("/images/literanusa-logo.jpeg", 40, 40);
        JLabel logoLabel;
        if (logoIcon != null) {
            logoLabel = new JLabel("LiteraNusa", logoIcon, SwingConstants.LEFT);
        } else {
            logoLabel = new JLabel("LiteraNusa");
        }
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(PRIMARY_TEAL);
        leftPanel.add(logoLabel);

        // Right side - Navigation menu
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(WHITE);

        // Navigation buttons
        JButton berandaBtn = createNavButton("Beranda", "/images/icons/home.png", true);
        JButton katalogBtn = createNavButton("Katalog", "/images/icons/catalog.png", false);
        JButton pinjamanBtn = createNavButton("Pinjaman Saya", "/images/icons/loans.png", false);
        JButton akunBtn = createNavButton("Akun", "/images/icons/user.png", false);

        // User greeting
        JLabel userGreeting = new JLabel("Halo, " + currentUser.getFullName());
        userGreeting.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userGreeting.setForeground(LIGHT_TEXT);

        rightPanel.add(berandaBtn);
        rightPanel.add(katalogBtn);
        rightPanel.add(pinjamanBtn);
        rightPanel.add(akunBtn);
        rightPanel.add(userGreeting);

        navBar.add(leftPanel, BorderLayout.WEST);
        navBar.add(rightPanel, BorderLayout.EAST);

        return navBar;
    }

    private JButton createNavButton(String text, String iconPath, boolean active) {
        // Load icon
        ImageIcon icon = ImageUtils.loadImageIcon(iconPath, 16, 16);

        JButton button = new JButton(text, icon);
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

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!active) {
                    button.setForeground(PRIMARY_TEAL);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!active) {
                    button.setForeground(LIGHT_TEXT);
                }
            }
        });

        // Add action listeners
        button.addActionListener(e -> {
            switch (text) {
                case "Pinjaman Saya":
                    showLoansDialog();
                    break;
                case "Akun":
                    new UserProfileView(currentUser).setVisible(true);
                    break;
                case "Katalog":
                    showCatalogDialog();
                    break;
            }
        });

        return button;
    }

    private JScrollPane createMainContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(LIGHT_GRAY);

        // Hero Section
        JPanel heroSection = createHeroSection();
        contentPanel.add(heroSection);

        // Popular Books Section
        JPanel popularBooksSection = createPopularBooksSection();
        contentPanel.add(popularBooksSection);

        // Category Filter Section
        JPanel categorySection = createCategorySection();
        contentPanel.add(categorySection);

        // Books by Category Section
        JPanel booksByCategorySection = createBooksByCategorySection();
        contentPanel.add(booksByCategorySection);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JPanel createHeroSection() {
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setPreferredSize(new Dimension(0, 200));

        // Create gradient background
        heroPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_TEAL,
                        getWidth(), getHeight(), SECONDARY_TEAL
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setPreferredSize(new Dimension(0, 200));
        heroPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        // Book icon dengan gambar
        ImageIcon bookIcon = ImageUtils.loadImageIcon("/images/icons/book-hero.png", 48, 48);
        JLabel bookIconLabel;
        if (bookIcon != null) {
            bookIconLabel = new JLabel(bookIcon);
        } else {
            bookIconLabel = new JLabel("📖");
            bookIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        }
        bookIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome title
        JLabel welcomeTitle = new JLabel("Selamat Datang di e-Library LiteraNusa");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeTitle.setForeground(WHITE);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Jelajahi koleksi buku digital terlengkap dan temukan bacaan favorit Anda");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        heroPanel.add(Box.createVerticalGlue());
        heroPanel.add(bookIconLabel);
        heroPanel.add(Box.createVerticalStrut(15));
        heroPanel.add(welcomeTitle);
        heroPanel.add(Box.createVerticalStrut(10));
        heroPanel.add(subtitle);
        heroPanel.add(Box.createVerticalGlue());

        return heroPanel;
    }

    private JPanel createPopularBooksSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(40, 30, 20, 30));

        // Section header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHITE);

        // Section title dengan icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(WHITE);

        ImageIcon popularIcon = ImageUtils.loadImageIcon("/images/icons/popular-books.png", 20, 20);
        JLabel iconLabel = new JLabel(popularIcon);
        JLabel sectionTitle = new JLabel("Buku Terpopuler");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(DARK_TEXT);

        if (popularIcon != null) {
            titlePanel.add(iconLabel);
            titlePanel.add(Box.createHorizontalStrut(8));
        }
        titlePanel.add(sectionTitle);

        // PERBAIKAN: Tombol "Lihat Semua" yang terhubung ke katalog
        JButton viewAllBtn = new JButton("Lihat Semua >");
        viewAllBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        viewAllBtn.setForeground(PRIMARY_TEAL);
        viewAllBtn.setBorderPainted(false);
        viewAllBtn.setContentAreaFilled(false);
        viewAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action listener untuk membuka katalog
        viewAllBtn.addActionListener(e -> showCatalogDialog());

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(viewAllBtn, BorderLayout.EAST);

        // Books grid
        JPanel booksGrid = new JPanel(new GridLayout(1, 5, 20, 0));
        booksGrid.setBackground(WHITE);
        booksGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Get top 5 books by rating
        List<Book> popularBooks = bookController.getAllBooks().stream()
                .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                .limit(5)
                .collect(Collectors.toList());

        for (Book book : popularBooks) {
            JPanel bookCard = createModernBookCard(book);
            booksGrid.add(bookCard);
        }

        section.add(headerPanel, BorderLayout.NORTH);
        section.add(booksGrid, BorderLayout.CENTER);

        return section;
    }

    private JPanel createModernBookCard(Book book) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(200, 280));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Book cover placeholder
        JPanel coverPanel = new JPanel();
        coverPanel.setBackground(LIGHT_GRAY);
        coverPanel.setPreferredSize(new Dimension(150, 180));
        coverPanel.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        // Try to load actual cover
        JLabel coverLabel = ImageUtils.createBookCoverLabel(book.getCoverImage(), 150, 180);
        if (coverLabel.getIcon() != null) {
            coverPanel.setLayout(new BorderLayout());
            coverPanel.add(coverLabel, BorderLayout.CENTER);
        } else {
            coverPanel.setLayout(new BorderLayout());
            // Gunakan icon gambar untuk placeholder
            ImageIcon bookPlaceholder = ImageUtils.loadImageIcon("/images/icons/book-placeholder.png", 48, 48);
            JLabel placeholderLabel;
            if (bookPlaceholder != null) {
                placeholderLabel = new JLabel(bookPlaceholder);
            } else {
                placeholderLabel = new JLabel("📖", SwingConstants.CENTER);
                placeholderLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            }
            placeholderLabel.setForeground(LIGHT_TEXT);
            coverPanel.add(placeholderLabel, BorderLayout.CENTER);
        }

        // Book info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Title and year
        JLabel titleLabel = new JLabel("<html><b>" + truncateText(book.getTitle(), 20) + "</b></html>");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(DARK_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel yearLabel = new JLabel("TAHUN - 2024");
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        yearLabel.setForeground(LIGHT_TEXT);
        yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Availability status dengan icon
        JLabel statusLabel;
        ImageIcon statusIcon;
        if (book.getAvailableCopies() > 0) {
            statusIcon = ImageUtils.loadImageIcon("/images/icons/available.png", 12, 12);
            if (statusIcon != null) {
                statusLabel = new JLabel("Tersedia", statusIcon, SwingConstants.LEFT);
            } else {
                statusLabel = new JLabel("✓ Tersedia");
            }
            statusLabel.setForeground(SUCCESS_GREEN);
        } else {
            statusIcon = ImageUtils.loadImageIcon("/images/icons/unavailable.png", 12, 12);
            if (statusIcon != null) {
                statusLabel = new JLabel("Tidak Tersedia", statusIcon, SwingConstants.LEFT);
            } else {
                statusLabel = new JLabel("✗ Tidak Tersedia");
            }
            statusLabel.setForeground(DANGER_RED);
        }
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Detail button
        JButton detailBtn = new JButton("Detail");
        detailBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailBtn.setBackground(SECONDARY_TEAL);
        detailBtn.setForeground(WHITE);
        detailBtn.setBorderPainted(false);
        detailBtn.setFocusPainted(false);
        detailBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        detailBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailBtn.setMaximumSize(new Dimension(100, 30));

        detailBtn.addActionListener(e -> new BookDetailView(book, currentUser).setVisible(true));

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(yearLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(statusLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(detailBtn);

        card.add(coverPanel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_TEAL, 2),
                        BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });

        return card;
    }

    private JPanel createCategorySection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(LIGHT_GRAY);
        section.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Section title dengan icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(LIGHT_GRAY);

        ImageIcon categoryIcon = ImageUtils.loadImageIcon("/images/icons/category.png", 18, 18);
        JLabel iconLabel = new JLabel(categoryIcon);
        JLabel sectionTitle = new JLabel("Populer Berdasarkan Kategori");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(DARK_TEXT);

        if (categoryIcon != null) {
            titlePanel.add(iconLabel);
            titlePanel.add(Box.createHorizontalStrut(8));
        }
        titlePanel.add(sectionTitle);

        // Category buttons
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryPanel.setBackground(LIGHT_GRAY);

        String[] categories = {"Drama", "Fantasi", "Romance", "Thriller", "Komedi", "Aksi", "Sejarah", "Inspiratif"};

        for (String category : categories) {
            JButton categoryBtn = createCategoryButton(category);
            categoryPanel.add(categoryBtn);
        }

        section.add(titlePanel, BorderLayout.NORTH);
        section.add(categoryPanel, BorderLayout.CENTER);

        return section;
    }

    private JButton createCategoryButton(String category) {
        JButton button = new JButton(category);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(WHITE);
        button.setForeground(LIGHT_TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set Drama as active by default
        if (category.equals("Drama")) {
            button.setBackground(PRIMARY_TEAL);
            button.setForeground(WHITE);
        }

        button.addActionListener(e -> {
            selectedGenre = category;
            filterBooksByGenre(category);
            updateCategoryButtons(button);
        });

        return button;
    }

    private void updateCategoryButtons(JButton activeButton) {
        Container parent = activeButton.getParent();
        for (Component comp : parent.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (btn == activeButton) {
                    btn.setBackground(PRIMARY_TEAL);
                    btn.setForeground(WHITE);
                } else {
                    btn.setBackground(WHITE);
                    btn.setForeground(LIGHT_TEXT);
                }
            }
        }
    }

    private JPanel createBooksByCategorySection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 30));

        // Section header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHITE);

        JLabel sectionTitle = new JLabel("Buku " + selectedGenre);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(DARK_TEXT);

        JComboBox<String> sortCombo = new JComboBox<>(new String[]{"TERBARU", "TERPOPULER", "A-Z"});
        sortCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sortCombo.setBackground(WHITE);

        headerPanel.add(sectionTitle, BorderLayout.WEST);
        headerPanel.add(sortCombo, BorderLayout.EAST);

        // Books grid
        booksCardsPanel = new JPanel(new GridLayout(0, 4, 20, 20));
        booksCardsPanel.setBackground(WHITE);
        booksCardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        section.add(headerPanel, BorderLayout.NORTH);
        section.add(booksCardsPanel, BorderLayout.CENTER);

        return section;
    }

    private JPanel createModernFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(PRIMARY_TEAL);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        footer.setPreferredSize(new Dimension(0, 60));

        // Footer logo dengan gambar
        ImageIcon footerIcon = ImageUtils.loadImageIcon("/images/literanusa-logo.jpeg", 40, 40);
        JLabel footerLabel;
        if (footerIcon != null) {
            footerLabel = new JLabel("LiteraNusa", footerIcon, SwingConstants.LEFT);
        } else {
            footerLabel = new JLabel("LiteraNusa");
        }
        footerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        footerLabel.setForeground(WHITE);

        JLabel copyrightLabel = new JLabel("Sistem Perpustakaan Digital Indonesia");
        copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyrightLabel.setForeground(new Color(255, 255, 255, 180));

        footer.setLayout(new BorderLayout());
        footer.add(footerLabel, BorderLayout.WEST);
        footer.add(copyrightLabel, BorderLayout.EAST);

        return footer;
    }

    private void loadBooks() {
        currentBooks = bookController.getAllBooks();
        filterBooksByGenre(selectedGenre);
    }

    private void filterBooksByGenre(String genre) {
        if (booksCardsPanel == null) return;

        booksCardsPanel.removeAll();

        List<Book> filteredBooks;
        if (genre.equals("Semua")) {
            filteredBooks = currentBooks;
        } else {
            filteredBooks = currentBooks.stream()
                    .filter(book -> genre.equalsIgnoreCase(book.getGenre()))
                    .collect(Collectors.toList());
        }

        for (Book book : filteredBooks) {
            JPanel card = createModernBookCard(book);
            booksCardsPanel.add(card);
        }

        booksCardsPanel.revalidate();
        booksCardsPanel.repaint();
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void showLoansDialog() {
        JDialog loansDialog = new JDialog(this, "Pinjaman Saya", true);
        loansDialog.setSize(900, 650);
        loansDialog.setLocationRelativeTo(this);
        loansDialog.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));

        // Title dengan icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(WHITE);

        ImageIcon loansIcon = ImageUtils.loadImageIcon("/images/icons/loans.png", 24, 24);
        JLabel iconLabel = new JLabel(loansIcon);
        JLabel titleLabel = new JLabel("Pinjaman Saya");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(DARK_TEXT);

        if (loansIcon != null) {
            titlePanel.add(iconLabel);
            titlePanel.add(Box.createHorizontalStrut(10));
        }
        titlePanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Kelola dan pantau status pinjaman buku Anda");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_TEXT);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Load user loans
        List<Loan> loans = loanDAO.getLoansByUserId(currentUser.getId());

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(LIGHT_GRAY);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        if (loans.isEmpty()) {
            // Empty state
            JPanel emptyPanel = createEmptyLoansPanel();
            contentPanel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            // Statistics Panel
            JPanel statsPanel = createLoansStatsPanel(loans);

            // Loans Cards Panel
            JPanel loansCardsPanel = createLoansCardsPanel(loans);

            contentPanel.add(statsPanel, BorderLayout.NORTH);
            contentPanel.add(loansCardsPanel, BorderLayout.CENTER);
        }

        // Footer Panel dengan action buttons
        JPanel footerPanel = createLoansFooterPanel();

        loansDialog.add(headerPanel, BorderLayout.NORTH);
        loansDialog.add(contentPanel, BorderLayout.CENTER);
        loansDialog.add(footerPanel, BorderLayout.SOUTH);
        loansDialog.setVisible(true);
    }

    private JPanel createEmptyLoansPanel() {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setBackground(WHITE);
        emptyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(60, 40, 60, 40)
        ));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(WHITE);

        // Empty icon
        ImageIcon emptyIcon = ImageUtils.loadImageIcon("/images/icons/empty-loans.png", 64, 64);
        JLabel emptyIconLabel;
        if (emptyIcon != null) {
            emptyIconLabel = new JLabel(emptyIcon);
        } else {
            emptyIconLabel = new JLabel("📚");
            emptyIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
            emptyIconLabel.setForeground(LIGHT_TEXT);
        }
        emptyIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emptyTitle = new JLabel("Belum Ada Pinjaman");
        emptyTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        emptyTitle.setForeground(DARK_TEXT);
        emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emptySubtitle = new JLabel("Mulai jelajahi katalog dan pinjam buku favorit Anda");
        emptySubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emptySubtitle.setForeground(LIGHT_TEXT);
        emptySubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton browseCatalogBtn = new JButton("Jelajahi Katalog");
        browseCatalogBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        browseCatalogBtn.setBackground(PRIMARY_TEAL);
        browseCatalogBtn.setForeground(WHITE);
        browseCatalogBtn.setBorderPainted(false);
        browseCatalogBtn.setFocusPainted(false);
        browseCatalogBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseCatalogBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseCatalogBtn.setMaximumSize(new Dimension(150, 40));

        browseCatalogBtn.addActionListener(e -> {
            ((JDialog) SwingUtilities.getWindowAncestor(browseCatalogBtn)).dispose();
            showCatalogDialog();
        });

        centerPanel.add(emptyIconLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(emptyTitle);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(emptySubtitle);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(browseCatalogBtn);

        emptyPanel.add(centerPanel, BorderLayout.CENTER);
        return emptyPanel;
    }

    private JPanel createLoansStatsPanel(List<Loan> loans) {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(LIGHT_GRAY);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Calculate statistics
        long totalLoans = loans.size();
        long activeLoans = loans.stream().filter(loan -> "ACTIVE".equals(loan.getStatus())).count();
        long overdueLoans = loans.stream().filter(loan -> {
            if ("ACTIVE".equals(loan.getStatus()) && loan.getDueDate() != null) {
                return loan.getDueDate().isBefore(LocalDate.now());
            }
            return false;
        }).count();
        long returnedLoans = loans.stream().filter(loan -> "RETURNED".equals(loan.getStatus())).count();

        // Create stat cards
        statsPanel.add(createStatCard("Total Pinjaman", String.valueOf(totalLoans), PRIMARY_TEAL, "/images/icons/total-loans.png"));
        statsPanel.add(createStatCard("Sedang Dipinjam", String.valueOf(activeLoans), SECONDARY_TEAL, "/images/icons/active-loans.png"));
        statsPanel.add(createStatCard("Terlambat", String.valueOf(overdueLoans), DANGER_RED, "/images/icons/overdue-loans.png"));
        statsPanel.add(createStatCard("Sudah Dikembalikan", String.valueOf(returnedLoans), SUCCESS_GREEN, "/images/icons/returned-loans.png"));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color color, String iconPath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Icon
        ImageIcon icon = ImageUtils.loadImageIcon(iconPath, 32, 32);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(LIGHT_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(valueLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(titleLabel);

        if (icon != null) {
            card.add(iconLabel, BorderLayout.NORTH);
            card.add(Box.createVerticalStrut(10));
        }
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createLoansCardsPanel(List<Loan> loans) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_GRAY);

        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(LIGHT_GRAY);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JButton allBtn = createFilterButton("Semua", true);
        JButton activeBtn = createFilterButton("Aktif", false);
        JButton overdueBtn = createFilterButton("Terlambat", false);
        JButton returnedBtn = createFilterButton("Dikembalikan", false);

        filterPanel.add(allBtn);
        filterPanel.add(activeBtn);
        filterPanel.add(overdueBtn);
        filterPanel.add(returnedBtn);

        // Loans grid
        JPanel loansGrid = new JPanel();
        loansGrid.setLayout(new BoxLayout(loansGrid, BoxLayout.Y_AXIS));
        loansGrid.setBackground(LIGHT_GRAY);

        // Create loan cards
        for (Loan loan : loans) {
            Book book = bookController.getBookById(loan.getBookId());
            JPanel loanCard = createModernLoanCard(loan, book);
            loansGrid.add(loanCard);
            loansGrid.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(loansGrid);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JButton createFilterButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (active) {
            button.setBackground(PRIMARY_TEAL);
            button.setForeground(WHITE);
        } else {
            button.setBackground(WHITE);
            button.setForeground(LIGHT_TEXT);
        }

        return button;
    }

    private JPanel createModernLoanCard(Loan loan, Book book) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(0, 120));

        // Left side - Book cover
        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setBackground(LIGHT_GRAY);
        coverPanel.setPreferredSize(new Dimension(80, 100));
        coverPanel.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JLabel coverLabel = ImageUtils.createBookCoverLabel(book != null ? book.getCoverImage() : null, 80, 100);
        if (coverLabel.getIcon() != null) {
            coverPanel.add(coverLabel, BorderLayout.CENTER);
        } else {
            ImageIcon bookPlaceholder = ImageUtils.loadImageIcon("/images/icons/book-placeholder.png", 40, 40);
            JLabel placeholderLabel;
            if (bookPlaceholder != null) {
                placeholderLabel = new JLabel(bookPlaceholder);
            } else {
                placeholderLabel = new JLabel("📖", SwingConstants.CENTER);
                placeholderLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
            }
            placeholderLabel.setForeground(LIGHT_TEXT);
            coverPanel.add(placeholderLabel, BorderLayout.CENTER);
        }

        // Center - Book info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel titleLabel = new JLabel(book != null ? book.getTitle() : "Unknown Book");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(DARK_TEXT);

        JLabel authorLabel = new JLabel(book != null ? "oleh " + book.getAuthor() : "Unknown Author");
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        authorLabel.setForeground(LIGHT_TEXT);

        JLabel loanDateLabel = new JLabel("Dipinjam: " + loan.getLoanDate());
        loanDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loanDateLabel.setForeground(LIGHT_TEXT);

        JLabel dueDateLabel = new JLabel("Jatuh tempo: " + loan.getDueDate());
        dueDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Check if overdue
        if ("ACTIVE".equals(loan.getStatus()) && loan.getDueDate() != null && loan.getDueDate().isBefore(LocalDate.now())) {
            dueDateLabel.setForeground(DANGER_RED);
            dueDateLabel.setText("⚠️ Terlambat: " + loan.getDueDate());
        } else {
            dueDateLabel.setForeground(LIGHT_TEXT);
        }

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(loanDateLabel);
        infoPanel.add(dueDateLabel);

        // Right side - Status and actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(WHITE);
        rightPanel.setPreferredSize(new Dimension(150, 100));

        // Action button
        JButton actionBtn = new JButton();
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionBtn.setBorderPainted(false);
        actionBtn.setFocusPainted(false);
        actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionBtn.setMaximumSize(new Dimension(120, 30));

        if ("ACTIVE".equals(loan.getStatus())) {
            actionBtn.setText("Perpanjang");
            actionBtn.setBackground(SECONDARY_TEAL);
            actionBtn.setForeground(WHITE);
            actionBtn.addActionListener(e -> extendLoan(loan));
        } else {
            actionBtn.setText("Detail");
            actionBtn.setBackground(LIGHT_GRAY);
            actionBtn.setForeground(DARK_TEXT);
            actionBtn.addActionListener(e -> showLoanDetail(loan, book));
        }

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(actionBtn);
        rightPanel.add(Box.createVerticalGlue());

        card.add(coverPanel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private JLabel createStatusBadge(String status) {
        JLabel badge = new JLabel();
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        badge.setHorizontalAlignment(SwingConstants.CENTER);

        switch (status) {
            case "ACTIVE":
                badge.setText("AKTIF");
                badge.setBackground(new Color(40, 167, 69, 20));
                badge.setForeground(SUCCESS_GREEN);
                break;
            case "RETURNED":
                badge.setText("DIKEMBALIKAN");
                badge.setBackground(new Color(108, 117, 125, 20));
                badge.setForeground(LIGHT_TEXT);
                break;
            case "OVERDUE":
                badge.setText("TERLAMBAT");
                badge.setBackground(new Color(220, 53, 69, 20));
                badge.setForeground(DANGER_RED);
                break;
            default:
                badge.setText(status);
                badge.setBackground(LIGHT_GRAY);
                badge.setForeground(DARK_TEXT);
        }

        return badge;
    }

    private JPanel createLoansFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(WHITE);
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        refreshBtn.setBackground(LIGHT_GRAY);
        refreshBtn.setForeground(DARK_TEXT);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton closeBtn = new JButton("Tutup");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setBackground(PRIMARY_TEAL);
        closeBtn.setForeground(WHITE);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        refreshBtn.addActionListener(e -> {
            // Refresh the dialog
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(refreshBtn);
            dialog.dispose();
            showLoansDialog();
        });

        closeBtn.addActionListener(e -> {
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(closeBtn);
            dialog.dispose();
        });

        footerPanel.add(refreshBtn);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(closeBtn);

        return footerPanel;
    }

    // Helper methods untuk actions
    private void extendLoan(Loan loan) {
        // Implementasi perpanjangan pinjaman
        JOptionPane.showMessageDialog(this,
                "Fitur perpanjangan pinjaman akan segera tersedia.\nSilakan hubungi pustakawan.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLoanDetail(Loan loan, Book book) {
        // Implementasi detail pinjaman
        String details = String.format(
                "Detail Pinjaman\n\n" +
                        "ID Pinjaman: %d\n" +
                        "Judul Buku: %s\n" +
                        "Penulis: %s\n" +
                        "Tanggal Pinjam: %s\n" +
                        "Tanggal Jatuh Tempo: %s\n" +
                        "Status: %s",
                loan.getId(),
                book != null ? book.getTitle() : "Unknown",
                book != null ? book.getAuthor() : "Unknown",
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getStatus()
        );

        JOptionPane.showMessageDialog(this, details, "Detail Pinjaman", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCatalogDialog() {
        JDialog catalogDialog = new JDialog(this, "Katalog Buku", true);
        catalogDialog.setSize(1000, 700);
        catalogDialog.setLocationRelativeTo(this);

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JButton searchBtn = new JButton("Cari");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setBackground(PRIMARY_TEAL);
        searchBtn.setForeground(WHITE);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resetBtn.setBackground(LIGHT_GRAY);
        resetBtn.setForeground(DARK_TEXT);
        resetBtn.setBorderPainted(false);
        resetBtn.setFocusPainted(false);
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add search icon
        ImageIcon searchIcon = ImageUtils.loadImageIcon("/images/icons/search.png", 16, 16);
        if (searchIcon != null) {
            searchBtn.setIcon(searchIcon);
        }

        searchPanel.add(new JLabel("Cari Buku:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetBtn);

        // Create books grid
        JPanel catalogGrid = new JPanel(new GridLayout(0, 4, 15, 15));
        catalogGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        catalogGrid.setBackground(WHITE);

        // Status panel untuk menampilkan hasil pencarian
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(LIGHT_GRAY);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(LIGHT_TEXT);
        statusPanel.add(statusLabel);

        // Method untuk memuat semua buku
        Runnable loadAllBooks = () -> {
            catalogGrid.removeAll();
            List<Book> allBooks = bookController.getAllBooks();

            for (Book book : allBooks) {
                catalogGrid.add(createModernBookCard(book));
            }

            statusLabel.setText("Menampilkan " + allBooks.size() + " buku");
            catalogGrid.revalidate();
            catalogGrid.repaint();
        };

        // Method untuk pencarian
        Runnable performSearch = () -> {
            String searchQuery = searchField.getText().trim().toLowerCase();

            if (searchQuery.isEmpty()) {
                loadAllBooks.run();
                return;
            }

            catalogGrid.removeAll();
            List<Book> allBooks = bookController.getAllBooks();
            List<Book> filteredBooks = allBooks.stream()
                    .filter(book ->
                            book.getTitle().toLowerCase().contains(searchQuery) ||
                                    book.getAuthor().toLowerCase().contains(searchQuery) ||
                                    book.getGenre().toLowerCase().contains(searchQuery)
                    )
                    .collect(Collectors.toList());

            if (filteredBooks.isEmpty()) {
                // Tampilkan pesan tidak ada hasil
                JPanel noResultPanel = new JPanel(new BorderLayout());
                noResultPanel.setBackground(WHITE);
                noResultPanel.setPreferredSize(new Dimension(catalogGrid.getWidth(), 200));

                JLabel noResultLabel = new JLabel("<html><center>" +
                        "<div style='text-align: center;'>" +
                        "<h2 style='color: #6c757d;'>Tidak ada hasil ditemukan</h2>" +
                        "<p style='color: #6c757d;'>Coba gunakan kata kunci yang berbeda</p>" +
                        "</div></center></html>");
                noResultLabel.setHorizontalAlignment(SwingConstants.CENTER);

                noResultPanel.add(noResultLabel, BorderLayout.CENTER);
                catalogGrid.add(noResultPanel);

                statusLabel.setText("Tidak ada hasil untuk \"" + searchQuery + "\"");
            } else {
                for (Book book : filteredBooks) {
                    catalogGrid.add(createModernBookCard(book));
                }
                statusLabel.setText("Ditemukan " + filteredBooks.size() + " buku untuk \"" + searchQuery + "\"");
            }

            catalogGrid.revalidate();
            catalogGrid.repaint();
        };

        // Event listeners untuk pencarian
        searchBtn.addActionListener(e -> performSearch.run());

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadAllBooks.run();
        });

        // Enter key untuk pencarian
        searchField.addActionListener(e -> performSearch.run());

        // Real-time search saat mengetik (opsional)
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            private Timer searchTimer;

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (searchTimer != null) {
                    searchTimer.stop();
                }

                // Delay 500ms setelah user berhenti mengetik
                searchTimer = new Timer(500, event -> performSearch.run());
                searchTimer.setRepeats(false);
                searchTimer.start();
            }
        });

        // Load semua buku saat dialog dibuka
        loadAllBooks.run();

        // Create scroll pane
        JScrollPane catalogScrollPane = new JScrollPane(catalogGrid);
        catalogScrollPane.setBorder(null);
        catalogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        catalogScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        catalogScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Assemble dialog
        catalogDialog.setLayout(new BorderLayout());
        catalogDialog.add(searchPanel, BorderLayout.NORTH);

        // Create center panel untuk status dan grid
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(statusPanel, BorderLayout.NORTH);
        centerPanel.add(catalogScrollPane, BorderLayout.CENTER);

        catalogDialog.add(centerPanel, BorderLayout.CENTER);
        catalogDialog.setVisible(true);
    }

    private void loadUserLoans() {
        // Implementation for loading user loans if needed
    }
}
