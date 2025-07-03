package com.literanusa.view;

import com.literanusa.model.Book;
import com.literanusa.model.User;
import com.literanusa.util.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class BookDetailView extends JFrame {
    private Book book;
    private User currentUser;

    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(245, 245, 245);
    private final Color DARK_TEXT = new Color(33, 37, 41);
    private final Color ACCENT_BLUE = new Color(0, 123, 255);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);

    public BookDetailView(Book book, User currentUser) {
        this.book = book;
        this.currentUser = currentUser;
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Detail Buku - " + book.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PRIMARY_TEAL);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_TEAL);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(PRIMARY_TEAL);
        leftPanel.setPreferredSize(new Dimension(250, 0));

        JLabel coverLabel = ImageUtils.createBookCoverLabel(book.getCoverImage(), 200, 280);
        leftPanel.add(coverLabel, BorderLayout.CENTER);

        JPanel ratingPanel = new JPanel(new FlowLayout());
        ratingPanel.setBackground(PRIMARY_TEAL);

        JLabel ratingLabel = new JLabel(String.format("⭐ %.1f / 5.0", book.getRating()));
        ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ratingLabel.setForeground(SECONDARY_TEAL);
        ratingPanel.add(ratingLabel);
        leftPanel.add(ratingPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(DARK_TEXT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        infoPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1;
        JLabel authorLabel = new JLabel(book.getAuthor());
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        authorLabel.setForeground(DARK_TEXT);
        infoPanel.add(authorLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(book.getIsbn() != null ? book.getIsbn() : "-"), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        infoPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        JLabel genreLabel = new JLabel(book.getGenre());
        genreLabel.setForeground(ACCENT_BLUE);
        genreLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(genreLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        infoPanel.add(new JLabel("Ketersediaan:"), gbc);
        gbc.gridx = 1;
        String availabilityText = book.getAvailableCopies() + " dari " + book.getTotalCopies() + " eksemplar";
        JLabel availabilityLabel = new JLabel(availabilityText);
        availabilityLabel.setForeground(book.getAvailableCopies() > 0 ? SUCCESS_GREEN : Color.RED);
        availabilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(availabilityLabel, gbc);

        contentPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel synopsisPanel = new JPanel(new BorderLayout());
        synopsisPanel.setBackground(Color.WHITE);
        synopsisPanel.setBorder(BorderFactory.createTitledBorder("Sinopsis"));

        JTextArea synopsisArea = new JTextArea(book.getSynopsis() != null ? book.getSynopsis() : "Sinopsis tidak tersedia.");
        synopsisArea.setEditable(false);
        synopsisArea.setLineWrap(true);
        synopsisArea.setWrapStyleWord(true);
        synopsisArea.setBackground(LIGHT_GRAY);
        synopsisArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        synopsisArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JScrollPane synopsisScroll = new JScrollPane(synopsisArea);
        synopsisScroll.setPreferredSize(new Dimension(0, 150));
        synopsisPanel.add(synopsisScroll, BorderLayout.CENTER);

        contentPanel.add(synopsisPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton borrowButton = new JButton("📖 Pinjam Buku");
        borrowButton.setBackground(PRIMARY_TEAL);
        borrowButton.setForeground(Color.WHITE);
        borrowButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        borrowButton.setEnabled(book.getAvailableCopies() > 0);
        borrowButton.addActionListener(e -> borrowBook());

        JButton addToWishlistButton = new JButton("❤️ Tambah ke Wishlist");
        addToWishlistButton.setBackground(new Color(255, 105, 180));
        addToWishlistButton.setForeground(Color.WHITE);
        addToWishlistButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addToWishlistButton.addActionListener(e -> addToWishlist());

        JButton rateButton = new JButton("⭐ Beri Rating");
        rateButton.setBackground(Color.ORANGE);
        rateButton.setForeground(Color.WHITE);
        rateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rateButton.addActionListener(e -> rateBook());

        JButton closeButton = new JButton("✕ Tutup");
        closeButton.setBackground(Color.GRAY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(borrowButton);
        buttonPanel.add(addToWishlistButton);
        buttonPanel.add(rateButton);
        buttonPanel.add(closeButton);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void borrowBook() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin meminjam buku:\n" + book.getTitle() + "?",
                "Konfirmasi Peminjaman",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Buku berhasil dipinjam!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void addToWishlist() {
        JOptionPane.showMessageDialog(this, "Buku ditambahkan ke wishlist!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rateBook() {
        String[] ratings = {"1 ⭐", "2 ⭐⭐", "3 ⭐⭐⭐", "4 ⭐⭐⭐⭐", "5 ⭐⭐⭐⭐⭐"};
        String selectedRating = (String) JOptionPane.showInputDialog(this,
                "Berikan rating untuk buku ini:",
                "Rating Buku",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ratings,
                ratings[4]);

        if (selectedRating != null) {
            String review = JOptionPane.showInputDialog(this, "Tulis review Anda (opsional):");
            JOptionPane.showMessageDialog(this, "Terima kasih atas rating dan review Anda!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
