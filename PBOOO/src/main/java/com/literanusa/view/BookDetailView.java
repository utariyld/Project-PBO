package com.literanusa.view;

import com.literanusa.model.Book;
import com.literanusa.model.User;
import com.literanusa.model.Loan;
import com.literanusa.util.ImageUtils;
import com.literanusa.util.NotificationSystem;
import com.literanusa.util.WishlistManager;
import com.literanusa.dao.LoanDAO;
import com.literanusa.dao.BookDAO;
import com.literanusa.factory.DAOFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class BookDetailView extends JFrame {
    private Book book;
    private User currentUser;
    private WishlistManager wishlistManager;
    private LoanDAO loanDAO;
    private BookDAO bookDAO;

    // UI Components
    private JButton borrowButton;
    private JButton wishlistButton;
    private JButton rateButton;
    private JLabel availabilityLabel;
    private JPanel starRatingPanel;
    private int currentUserRating = 0;

    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(245, 245, 245);
    private final Color DARK_TEXT = new Color(33, 37, 41);
    private final Color ACCENT_BLUE = new Color(0, 123, 255);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private final Color DANGER_RED = new Color(220, 53, 69);
    private final Color WARNING_ORANGE = new Color(255, 193, 7);

    public BookDetailView(Book book, User currentUser) {
        this.book = book;
        this.currentUser = currentUser;
        this.wishlistManager = WishlistManager.getInstance();
        this.loanDAO = DAOFactory.getInstance().getLoanDAO();
        this.bookDAO = DAOFactory.getInstance().getBookDAO();
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Detail Buku - " + book.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_GRAY);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradient background
                ImageUtils.paintGradientBackground(g, this, LIGHT_GRAY, Color.WHITE);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left Panel - Book Cover and Quick Info
        JPanel leftPanel = createLeftPanel();
        
        // Right Panel - Detailed Information
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Book Cover
        JLabel coverLabel = ImageUtils.createBookCoverLabel(book.getCoverImage(), 220, 320);
        coverLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Rating Display
        JPanel ratingDisplayPanel = createRatingDisplayPanel();

        // Quick Stats
        JPanel statsPanel = createQuickStatsPanel();

        leftPanel.add(coverLabel, BorderLayout.CENTER);
        leftPanel.add(ratingDisplayPanel, BorderLayout.NORTH);
        leftPanel.add(statsPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createRatingDisplayPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);

        JLabel ratingLabel = new JLabel(String.format("%.1f", book.getRating()));
        ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        ratingLabel.setForeground(WARNING_ORANGE);

        JPanel starsPanel = createStarsDisplay(book.getRating());

        JLabel reviewsLabel = new JLabel("(" + getReviewCount() + " ulasan)");
        reviewsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reviewsLabel.setForeground(new Color(108, 117, 125));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        starsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(ratingLabel);
        container.add(starsPanel);
        container.add(reviewsLabel);

        panel.add(container);
        return panel;
    }

    private JPanel createStarsDisplay(double rating) {
        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        starsPanel.setBackground(Color.WHITE);

        for (int i = 1; i <= 5; i++) {
            JLabel star = new JLabel();
            star.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            
            if (i <= rating) {
                star.setText("⭐");
            } else if (i - 0.5 <= rating) {
                star.setText("⭐");
                star.setForeground(new Color(255, 193, 7, 150));
            } else {
                star.setText("☆");
                star.setForeground(new Color(222, 226, 230));
            }
            
            starsPanel.add(star);
        }

        return starsPanel;
    }

    private JPanel createQuickStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Availability
        String availabilityText = book.getAvailableCopies() + " dari " + book.getTotalCopies() + " tersedia";
        availabilityLabel = new JLabel(availabilityText, SwingConstants.CENTER);
        availabilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        availabilityLabel.setForeground(book.getAvailableCopies() > 0 ? SUCCESS_GREEN : DANGER_RED);

        // Wishlist count
        int wishlistCount = getWishlistCount();
        JLabel wishlistCountLabel = new JLabel("❤ " + wishlistCount + " di wishlist", SwingConstants.CENTER);
        wishlistCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        wishlistCountLabel.setForeground(new Color(108, 117, 125));

        // Genre badge
        JLabel genreLabel = new JLabel(book.getGenre(), SwingConstants.CENTER);
        genreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        genreLabel.setForeground(ACCENT_BLUE);
        genreLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panel.add(availabilityLabel);
        panel.add(wishlistCountLabel);
        panel.add(genreLabel);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header with title and author
        JPanel headerPanel = createHeaderPanel();
        
        // Content with details and synopsis
        JPanel contentPanel = createContentPanel();
        
        // Interactive rating panel
        JPanel ratingPanel = createInteractiveRatingPanel();
        
        // Action buttons
        JPanel buttonPanel = createActionButtonsPanel();

        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(ratingPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("<html><h2>" + book.getTitle() + "</h2></html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(DARK_TEXT);

        JLabel authorLabel = new JLabel("Oleh: " + book.getAuthor());
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        authorLabel.setForeground(new Color(108, 117, 125));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(authorLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Book details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            "Informasi Buku",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            DARK_TEXT
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addDetailRow(detailsPanel, gbc, "ISBN:", book.getIsbn() != null ? book.getIsbn() : "Tidak tersedia", 0);
        addDetailRow(detailsPanel, gbc, "Genre:", book.getGenre(), 1);
        addDetailRow(detailsPanel, gbc, "Halaman:", "200-300 halaman", 2);
        addDetailRow(detailsPanel, gbc, "Bahasa:", "Bahasa Indonesia", 3);

        // Synopsis
        JPanel synopsisPanel = new JPanel(new BorderLayout());
        synopsisPanel.setBackground(Color.WHITE);
        synopsisPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            "Sinopsis",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            DARK_TEXT
        ));

        JTextArea synopsisArea = new JTextArea(book.getSynopsis() != null ? book.getSynopsis() : "Sinopsis tidak tersedia untuk buku ini.");
        synopsisArea.setEditable(false);
        synopsisArea.setLineWrap(true);
        synopsisArea.setWrapStyleWord(true);
        synopsisArea.setBackground(new Color(248, 249, 250));
        synopsisArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        synopsisArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane synopsisScroll = new JScrollPane(synopsisArea);
        synopsisScroll.setBorder(null);
        synopsisScroll.setPreferredSize(new Dimension(0, 120));
        synopsisPanel.add(synopsisScroll, BorderLayout.CENTER);

        panel.add(detailsPanel, BorderLayout.NORTH);
        panel.add(synopsisPanel, BorderLayout.CENTER);

        return panel;
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelComponent.setForeground(DARK_TEXT);
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valueComponent.setForeground(new Color(108, 117, 125));
        panel.add(valueComponent, gbc);
    }

    private JPanel createInteractiveRatingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            "Berikan Rating Anda",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            DARK_TEXT
        ));

        starRatingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        starRatingPanel.setBackground(Color.WHITE);

        for (int i = 1; i <= 5; i++) {
            final int rating = i;
            JLabel star = new JLabel("☆");
            star.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            star.setForeground(new Color(222, 226, 230));
            star.setCursor(new Cursor(Cursor.HAND_CURSOR));

            star.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    updateStarHover(rating);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateStarDisplay(currentUserRating);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    currentUserRating = rating;
                    updateStarDisplay(rating);
                    submitRating(rating);
                }
            });

            starRatingPanel.add(star);
        }

        JLabel ratingInstructions = new JLabel("Klik bintang untuk memberikan rating", SwingConstants.CENTER);
        ratingInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ratingInstructions.setForeground(new Color(108, 117, 125));

        panel.add(starRatingPanel, BorderLayout.CENTER);
        panel.add(ratingInstructions, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(Color.WHITE);

        borrowButton = createStyledButton(
            book.getAvailableCopies() > 0 ? "📖 Pinjam Buku" : "📖 Tidak Tersedia",
            SECONDARY_TEAL,
            Color.WHITE,
            book.getAvailableCopies() > 0
        );
        borrowButton.addActionListener(e -> borrowBook());

        wishlistButton = createStyledButton(
            wishlistManager.isInWishlist(currentUser, book) ? "💔 Hapus dari Wishlist" : "❤️ Tambah ke Wishlist",
            new Color(255, 105, 180),
            Color.WHITE,
            true
        );
        wishlistButton.addActionListener(e -> toggleWishlist());

        rateButton = createStyledButton("⭐ Lihat Ulasan", WARNING_ORANGE, Color.WHITE, true);
        rateButton.addActionListener(e -> showReviews());

        JButton closeButton = createStyledButton("✕ Tutup", new Color(108, 117, 125), Color.WHITE, true);
        closeButton.addActionListener(e -> dispose());

        panel.add(borrowButton);
        panel.add(wishlistButton);
        panel.add(rateButton);
        panel.add(closeButton);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor, boolean enabled) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color currentBgColor = bgColor;
                if (!isEnabled()) {
                    currentBgColor = new Color(222, 226, 230);
                } else if (getModel().isPressed()) {
                    currentBgColor = bgColor.darker();
                } else if (getModel().isRollover()) {
                    currentBgColor = bgColor.brighter();
                }

                g2d.setColor(currentBgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Draw text
                g2d.setColor(isEnabled() ? textColor : new Color(108, 117, 125));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
            }
        };

        button.setPreferredSize(new Dimension(160, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setEnabled(enabled);

        return button;
    }

    // Helper methods
    private int getReviewCount() {
        // Simulate review count - in real app, this would query database
        return (int) (Math.random() * 100) + 10;
    }

    private int getWishlistCount() {
        // Get actual wishlist count from WishlistManager
        return wishlistManager.getWishlistStatistics().getOrDefault(book.getId(), 0);
    }

    private void updateStarHover(int rating) {
        Component[] stars = starRatingPanel.getComponents();
        for (int i = 0; i < stars.length; i++) {
            JLabel star = (JLabel) stars[i];
            if (i < rating) {
                star.setText("⭐");
                star.setForeground(WARNING_ORANGE);
            } else {
                star.setText("☆");
                star.setForeground(new Color(222, 226, 230));
            }
        }
    }

    private void updateStarDisplay(int rating) {
        Component[] stars = starRatingPanel.getComponents();
        for (int i = 0; i < stars.length; i++) {
            JLabel star = (JLabel) stars[i];
            if (i < rating) {
                star.setText("⭐");
                star.setForeground(WARNING_ORANGE);
            } else {
                star.setText("☆");
                star.setForeground(new Color(222, 226, 230));
            }
        }
    }

    private void submitRating(int rating) {
        // Show confirmation dialog with review option
        int option = JOptionPane.showConfirmDialog(this,
                "Anda memberikan " + rating + " bintang untuk buku ini.\nApakah Anda ingin menambahkan ulasan?",
                "Rating Diberikan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            showReviewDialog(rating);
        } else {
            // Just submit rating without review
            NotificationSystem.showRatingSubmitted(this, book.getTitle(), rating);
        }
    }

    private void showReviewDialog(int rating) {
        JDialog reviewDialog = new JDialog(this, "Tulis Ulasan", true);
        reviewDialog.setSize(500, 350);
        reviewDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("Tulis ulasan untuk: " + book.getTitle());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Rating display
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ratingLabel = new JLabel("Rating Anda: ");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        for (int i = 0; i < rating; i++) {
            JLabel star = new JLabel("⭐");
            star.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            ratingPanel.add(star);
        }
        ratingPanel.add(ratingLabel);

        // Review text area
        JTextArea reviewArea = new JTextArea(8, 40);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reviewArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(reviewArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ulasan (Opsional)"));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Kirim Ulasan");
        JButton cancelButton = new JButton("Batal");

        submitButton.setBackground(SECONDARY_TEAL);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));

        submitButton.addActionListener(e -> {
            String reviewText = reviewArea.getText().trim();
            // Here you would save the rating and review to database
            reviewDialog.dispose();
            NotificationSystem.showRatingSubmitted(this, book.getTitle(), rating);
        });

        cancelButton.addActionListener(e -> reviewDialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        panel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(ratingPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        reviewDialog.add(panel);
        reviewDialog.setVisible(true);
    }

    private void borrowBook() {
        if (book.getAvailableCopies() <= 0) {
            NotificationSystem.showError(this, "Buku ini sedang tidak tersedia untuk dipinjam.");
            return;
        }

        // Check if user already has this book on loan
        // This would typically check the database
        boolean alreadyBorrowed = false; // Simulate check
        
        if (alreadyBorrowed) {
            NotificationSystem.showWarning(this, "Anda sudah meminjam buku ini. Silakan kembalikan terlebih dahulu.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><div style='text-align: center;'>" +
                "<h3>Konfirmasi Peminjaman</h3>" +
                "<p>Buku: <b>" + book.getTitle() + "</b></p>" +
                "<p>Penulis: " + book.getAuthor() + "</p>" +
                "<p>Durasi pinjam: 14 hari</p>" +
                "<p>Tanggal kembali: " + LocalDate.now().plusDays(14) + "</p>" +
                "</div></html>",
                "Pinjam Buku",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Show loading dialog
            JDialog loadingDialog = NotificationSystem.showLoadingDialog(this, "Memproses peminjaman...");
            loadingDialog.setVisible(true);

            // Simulate processing time
            Timer timer = new Timer(2000, e -> {
                loadingDialog.dispose();
                
                try {
                    // Create loan record
                    Loan loan = new Loan();
                    loan.setUserId(currentUser.getId());
                    loan.setBookId(book.getId());
                    loan.setLoanDate(LocalDate.now());
                    loan.setDueDate(LocalDate.now().plusDays(14));
                    loan.setStatus(Loan.Status.ACTIVE);

                    // Save loan (this would use actual DAO)
                    // loanDAO.create(loan);

                    // Update book availability
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    // bookDAO.update(book);

                    // Update UI
                    updateAvailabilityDisplay();
                    updateBorrowButton();

                    // Show success notification
                    NotificationSystem.showBookBorrowed(this, book.getTitle());

                } catch (Exception ex) {
                    NotificationSystem.showError(this, "Terjadi kesalahan saat memproses peminjaman. Silakan coba lagi.");
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void toggleWishlist() {
        boolean wasInWishlist = wishlistManager.isInWishlist(currentUser, book);
        boolean success = wishlistManager.toggleWishlist(currentUser, book);

        if (success) {
            if (wasInWishlist) {
                // Removed from wishlist
                wishlistButton.setText("❤️ Tambah ke Wishlist");
                NotificationSystem.showInfo(this, "Buku dihapus dari wishlist");
            } else {
                // Added to wishlist
                wishlistButton.setText("💔 Hapus dari Wishlist");
                NotificationSystem.showWishlistAdded(this, book.getTitle());
            }
        } else {
            NotificationSystem.showError(this, "Terjadi kesalahan saat mengupdate wishlist.");
        }
    }

    private void showReviews() {
        // Create reviews dialog
        JDialog reviewsDialog = new JDialog(this, "Ulasan - " + book.getTitle(), true);
        reviewsDialog.setSize(600, 500);
        reviewsDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header with rating summary
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Ulasan untuk: " + book.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel avgRating = new JLabel(String.format("%.1f", book.getRating()));
        avgRating.setFont(new Font("Segoe UI", Font.BOLD, 24));
        avgRating.setForeground(WARNING_ORANGE);

        JPanel stars = createStarsDisplay(book.getRating());
        JLabel reviewCount = new JLabel("(" + getReviewCount() + " ulasan)");
        reviewCount.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        summaryPanel.add(avgRating);
        summaryPanel.add(stars);
        summaryPanel.add(reviewCount);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(summaryPanel, BorderLayout.SOUTH);

        // Sample reviews
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));

        // Add sample reviews
        String[] sampleReviews = {
            "Buku yang sangat bagus! Ceritanya menarik dan karakternya well-developed.",
            "Saya sangat menikmati membaca buku ini. Recommended!",
            "Plot twist di akhir cerita sangat mengejutkan. Must read!",
            "Gaya penulisan yang sangat mudah dipahami dan engaging."
        };

        String[] reviewers = {"Andi Wijaya", "Sari Melati", "Budi Santoso", "Lisa Permata"};
        int[] ratings = {5, 4, 5, 4};

        for (int i = 0; i < sampleReviews.length; i++) {
            JPanel reviewCard = createReviewCard(reviewers[i], ratings[i], sampleReviews[i]);
            reviewsPanel.add(reviewCard);
            reviewsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setBorder(null);

        // Close button
        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> reviewsDialog.dispose());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        reviewsDialog.add(panel);
        reviewsDialog.setVisible(true);
    }

    private JPanel createReviewCard(String reviewer, int rating, String review) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        // Header with reviewer name and rating
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(reviewer);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        for (int i = 0; i < rating; i++) {
            JLabel star = new JLabel("⭐");
            star.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
            ratingPanel.add(star);
        }

        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(ratingPanel, BorderLayout.EAST);

        // Review text
        JLabel reviewLabel = new JLabel("<html><div style='margin-top: 5px;'>" + review + "</div></html>");
        reviewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(reviewLabel, BorderLayout.CENTER);

        return card;
    }

    private void updateAvailabilityDisplay() {
        String availabilityText = book.getAvailableCopies() + " dari " + book.getTotalCopies() + " tersedia";
        availabilityLabel.setText(availabilityText);
        availabilityLabel.setForeground(book.getAvailableCopies() > 0 ? SUCCESS_GREEN : DANGER_RED);
    }

    private void updateBorrowButton() {
        if (book.getAvailableCopies() > 0) {
            borrowButton.setText("📖 Pinjam Buku");
            borrowButton.setEnabled(true);
        } else {
            borrowButton.setText("📖 Tidak Tersedia");
            borrowButton.setEnabled(false);
        }
        borrowButton.repaint();
    }
}
