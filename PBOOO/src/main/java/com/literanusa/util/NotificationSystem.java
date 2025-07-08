package com.literanusa.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotificationSystem {
    
    public enum NotificationType {
        SUCCESS(new Color(40, 167, 69), "✓"),
        ERROR(new Color(220, 53, 69), "✗"),
        WARNING(new Color(255, 193, 7), "⚠"),
        INFO(new Color(23, 162, 184), "ℹ"),
        BOOK_BORROWED(new Color(72, 201, 176), "📖"),
        BOOK_RETURNED(new Color(95, 158, 160), "📚"),
        WISHLIST_ADDED(new Color(255, 105, 180), "❤"),
        RATING_SUBMITTED(new Color(255, 165, 0), "⭐");
        
        private final Color color;
        private final String icon;
        
        NotificationType(Color color, String icon) {
            this.color = color;
            this.icon = icon;
        }
        
        public Color getColor() { return color; }
        public String getIcon() { return icon; }
    }
    
    private static final int NOTIFICATION_WIDTH = 350;
    private static final int NOTIFICATION_HEIGHT = 80;
    private static final int ANIMATION_DURATION = 300;
    private static final int DISPLAY_DURATION = 3000;
    
    public static void showNotification(Component parent, String title, String message, NotificationType type) {
        SwingUtilities.invokeLater(() -> {
            JWindow notification = createNotificationWindow(parent, title, message, type);
            animateNotification(notification, true, () -> {
                Timer dismissTimer = new Timer(DISPLAY_DURATION, e -> {
                    animateNotification(notification, false, notification::dispose);
                });
                dismissTimer.setRepeats(false);
                dismissTimer.start();
            });
        });
    }
    
    public static void showPersistentNotification(Component parent, String title, String message, NotificationType type, Runnable onAction) {
        SwingUtilities.invokeLater(() -> {
            JWindow notification = createPersistentNotificationWindow(parent, title, message, type, onAction);
            animateNotification(notification, true, null);
        });
    }
    
    private static JWindow createNotificationWindow(Component parent, String title, String message, NotificationType type) {
        JWindow window = new JWindow();
        window.setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        window.setAlwaysOnTop(true);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, Color.WHITE,
                    0, getHeight(), new Color(248, 249, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Colored left border
                g2d.setColor(type.getColor());
                g2d.fillRoundRect(0, 0, 5, getHeight(), 5, 5);
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Icon panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(type.getIcon());
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setForeground(type.getColor());
        iconPanel.add(iconLabel);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(33, 37, 41));
        
        JLabel messageLabel = new JLabel("<html><div style='width: 250px;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(new Color(108, 117, 125));
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        
        // Close button
        JLabel closeButton = new JLabel("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setForeground(new Color(108, 117, 125));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setHorizontalAlignment(SwingConstants.CENTER);
        closeButton.setPreferredSize(new Dimension(20, 20));
        
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                animateNotification(window, false, window::dispose);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(Color.BLACK);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(new Color(108, 117, 125));
            }
        });
        
        panel.add(iconPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.EAST);
        
        window.add(panel);
        positionNotification(window, parent);
        
        return window;
    }
    
    private static JWindow createPersistentNotificationWindow(Component parent, String title, String message, NotificationType type, Runnable onAction) {
        JWindow window = new JWindow();
        window.setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT + 30);
        window.setAlwaysOnTop(true);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, Color.WHITE,
                    0, getHeight(), new Color(248, 249, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Colored left border
                g2d.setColor(type.getColor());
                g2d.fillRoundRect(0, 0, 5, getHeight(), 5, 5);
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Icon panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(type.getIcon());
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setForeground(type.getColor());
        iconPanel.add(iconLabel);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(33, 37, 41));
        
        JLabel messageLabel = new JLabel("<html><div style='width: 220px;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(new Color(108, 117, 125));
        
        // Action button
        JButton actionButton = new JButton("Lihat");
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        actionButton.setBackground(type.getColor());
        actionButton.setForeground(Color.WHITE);
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionButton.setPreferredSize(new Dimension(60, 25));
        
        actionButton.addActionListener(e -> {
            if (onAction != null) {
                onAction.run();
            }
            animateNotification(window, false, window::dispose);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(actionButton);
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Close button
        JLabel closeButton = new JLabel("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setForeground(new Color(108, 117, 125));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setHorizontalAlignment(SwingConstants.CENTER);
        closeButton.setPreferredSize(new Dimension(20, 20));
        
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                animateNotification(window, false, window::dispose);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(Color.BLACK);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(new Color(108, 117, 125));
            }
        });
        
        panel.add(iconPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.EAST);
        
        window.add(panel);
        positionNotification(window, parent);
        
        return window;
    }
    
    private static void positionNotification(JWindow window, Component parent) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - NOTIFICATION_WIDTH - 20);
        int y = 20;
        
        // Check for other notifications and position accordingly
        Window[] windows = Window.getWindows();
        int notificationCount = 0;
        for (Window w : windows) {
            if (w instanceof JWindow && w.isVisible()) {
                notificationCount++;
            }
        }
        
        y += notificationCount * (NOTIFICATION_HEIGHT + 10);
        window.setLocation(x, y);
    }
    
    private static void animateNotification(JWindow window, boolean slideIn, Runnable onComplete) {
        Point targetLocation = window.getLocation();
        Point startLocation = new Point(
            slideIn ? targetLocation.x + NOTIFICATION_WIDTH : targetLocation.x,
            targetLocation.y
        );
        Point endLocation = slideIn ? targetLocation : new Point(targetLocation.x + NOTIFICATION_WIDTH, targetLocation.y);
        
        if (slideIn) {
            window.setLocation(startLocation);
            window.setVisible(true);
        }
        
        Timer animationTimer = new Timer(10, null);
        long startTime = System.currentTimeMillis();
        
        animationTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                float progress = Math.min(1.0f, (float) elapsed / ANIMATION_DURATION);
                
                // Easing function (ease-out)
                progress = 1 - (1 - progress) * (1 - progress);
                
                int currentX = (int) (startLocation.x + (endLocation.x - startLocation.x) * progress);
                window.setLocation(currentX, targetLocation.y);
                
                // Set opacity for fade effect
                float opacity = slideIn ? progress : 1 - progress;
                window.setOpacity(Math.max(0.1f, opacity));
                
                if (progress >= 1.0f) {
                    animationTimer.stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });
        
        animationTimer.start();
    }
    
    // Convenience methods for common notifications
    public static void showSuccess(Component parent, String message) {
        showNotification(parent, "Berhasil", message, NotificationType.SUCCESS);
    }
    
    public static void showError(Component parent, String message) {
        showNotification(parent, "Error", message, NotificationType.ERROR);
    }
    
    public static void showInfo(Component parent, String message) {
        showNotification(parent, "Informasi", message, NotificationType.INFO);
    }
    
    public static void showWarning(Component parent, String message) {
        showNotification(parent, "Peringatan", message, NotificationType.WARNING);
    }
    
    public static void showBookBorrowed(Component parent, String bookTitle) {
        showNotification(parent, "Buku Dipinjam", 
            "Anda telah meminjam: " + bookTitle, NotificationType.BOOK_BORROWED);
    }
    
    public static void showBookReturned(Component parent, String bookTitle) {
        showNotification(parent, "Buku Dikembalikan", 
            "Anda telah mengembalikan: " + bookTitle, NotificationType.BOOK_RETURNED);
    }
    
    public static void showWishlistAdded(Component parent, String bookTitle) {
        showNotification(parent, "Ditambahkan ke Wishlist", 
            "Buku '" + bookTitle + "' ditambahkan ke wishlist", NotificationType.WISHLIST_ADDED);
    }
    
    public static void showRatingSubmitted(Component parent, String bookTitle, double rating) {
        showNotification(parent, "Rating Diberikan", 
            String.format("Anda memberikan rating %.1f untuk '%s'", rating, bookTitle), 
            NotificationType.RATING_SUBMITTED);
    }
    
    // Loading dialog with modern design
    public static JDialog showLoadingDialog(Component parent, String message) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), true);
        dialog.setUndecorated(true);
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, Color.WHITE,
                    0, getHeight(), new Color(248, 249, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(new Color(222, 226, 230));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(33, 37, 41));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(248, 249, 250));
        progressBar.setForeground(new Color(72, 201, 176));
        progressBar.setBorderPainted(false);
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        
        dialog.add(panel);
        
        return dialog;
    }
}