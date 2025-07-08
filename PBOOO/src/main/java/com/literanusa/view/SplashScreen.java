package com.literanusa.view;

import com.literanusa.util.ImageUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    
    private JProgressBar progressBar;
    private JLabel loadingLabel;
    private Timer animationTimer;
    private int animationStep = 0;

    public SplashScreen() {
        createSplashScreen();
        showSplashScreen();
    }

    private void createSplashScreen() {
        // Custom panel with gradient background and animations
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Animated gradient background
                float alpha = 0.8f + 0.2f * (float) Math.sin(animationStep * 0.1);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(PRIMARY_TEAL.getRed(), PRIMARY_TEAL.getGreen(), PRIMARY_TEAL.getBlue(), (int)(255 * alpha)),
                    getWidth(), getHeight(), new Color(SECONDARY_TEAL.getRed(), SECONDARY_TEAL.getGreen(), SECONDARY_TEAL.getBlue(), (int)(255 * alpha))
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Floating circles animation
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < 5; i++) {
                    int x = (int) (50 + i * 80 + 20 * Math.sin(animationStep * 0.05 + i));
                    int y = (int) (100 + 30 * Math.cos(animationStep * 0.03 + i));
                    int size = 20 + i * 5;
                    g2d.fillOval(x, y, size, size);
                }
                
                // Subtle border with glow effect
                g2d.setStroke(new BasicStroke(3f));
                g2d.setColor(new Color(SECONDARY_TEAL.getRed(), SECONDARY_TEAL.getGreen(), SECONDARY_TEAL.getBlue(), 200));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
            }
        };
        
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Logo section with animation
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);

        ImageIcon logoIcon = ImageUtils.loadImageIcon("/images/literanusa-logo.jpeg", 120, 120);
        JLabel logoLabel;
        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon);
        } else {
            logoLabel = new JLabel("📚", SwingConstants.CENTER);
            logoLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 64));
            logoLabel.setForeground(LIGHT_GRAY);
        }

        // Add subtle animation to logo
        Timer logoAnimation = new Timer(100, e -> {
            float scale = 1.0f + 0.05f * (float) Math.sin(animationStep * 0.1);
            // Note: In a real implementation, you'd apply transform to the logo
            animationStep++;
            repaint();
        });
        logoAnimation.start();

        logoPanel.add(logoLabel);

        // Title section with modern typography
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("LiteraNusa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sistem Perpustakaan Digital Modern", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadingLabel = new JLabel("Memuat aplikasi...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        loadingLabel.setForeground(new Color(255, 255, 255, 180));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(loadingLabel);

        // Modern progress bar
        progressBar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Progress fill
                if (getValue() > 0) {
                    int fillWidth = (int) ((double) getValue() / getMaximum() * getWidth());
                    GradientPaint progressGradient = new GradientPaint(
                        0, 0, SECONDARY_TEAL,
                        fillWidth, 0, new Color(SECONDARY_TEAL.getRed(), SECONDARY_TEAL.getGreen(), SECONDARY_TEAL.getBlue(), 200)
                    );
                    g2d.setPaint(progressGradient);
                    g2d.fillRoundRect(0, 0, fillWidth, getHeight(), 10, 10);
                }
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(false);
        progressBar.setPreferredSize(new Dimension(300, 8));

        // Bottom panel for progress
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(progressBar);

        // Version info
        JLabel versionLabel = new JLabel("v1.0.0 - Beta", SwingConstants.CENTER);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        versionLabel.setForeground(new Color(255, 255, 255, 120));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout assembly
        panel.add(logoPanel, BorderLayout.NORTH);
        panel.add(titlePanel, BorderLayout.CENTER);
        
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);
        progressPanel.add(bottomPanel, BorderLayout.CENTER);
        progressPanel.add(versionLabel, BorderLayout.SOUTH);
        
        panel.add(progressPanel, BorderLayout.SOUTH);

        add(panel);
        setSize(450, 350);
        setLocationRelativeTo(null);
        
        // Set window to be semi-transparent
        setOpacity(0.95f);
    }

    private void showSplashScreen() {
        setVisible(true);

        // Simulate realistic loading process with progress updates
        String[] loadingSteps = {
            "Memuat konfigurasi...",
            "Menghubungkan ke database...",
            "Memuat data buku...",
            "Memuat data pengguna...",
            "Menginisialisasi komponen...",
            "Menyiapkan antarmuka...",
            "Siap digunakan!"
        };

        Timer progressTimer = new Timer(400, new ActionListener() {
            private int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step < loadingSteps.length) {
                    loadingLabel.setText(loadingSteps[step]);
                    
                    // Update progress
                    int progress = (int) ((step + 1.0) / loadingSteps.length * 100);
                    progressBar.setValue(progress);
                    
                    // Add some randomness to make it feel more realistic
                    if (step == 1) { // Database connection step takes longer
                        Timer.class.cast(e.getSource()).setDelay(800);
                    } else if (step == 2) { // Loading books takes longer
                        Timer.class.cast(e.getSource()).setDelay(600);
                    } else {
                        Timer.class.cast(e.getSource()).setDelay(400);
                    }
                    
                    step++;
                } else {
                    // Loading complete
                    Timer.class.cast(e.getSource()).stop();
                    
                    // Add a small delay before transitioning
                    Timer closeTimer = new Timer(500, closeEvent -> {
                        // Fade out animation
                        animateClose();
                        Timer.class.cast(closeEvent.getSource()).stop();
                    });
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                }
            }
        });

        progressTimer.start();
    }

    private void animateClose() {
        Timer fadeTimer = new Timer(50, new ActionListener() {
            private float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.1f;
                if (opacity <= 0.0f) {
                    Timer.class.cast(e.getSource()).stop();
                    setVisible(false);
                    dispose();
                    // Show login screen
                    SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
                } else {
                    setOpacity(Math.max(0.1f, opacity));
                }
            }
        });
        
        fadeTimer.start();
    }
}
