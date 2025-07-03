package com.literanusa.view;

import com.literanusa.util.ImageUtils;
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);

    public SplashScreen() {
        createSplashScreen();
        showSplashScreen();
    }

    private void createSplashScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_TEAL);
        panel.setBorder(BorderFactory.createLineBorder(SECONDARY_TEAL, 3));

        // Logo section
        JPanel logoPanel = new JPanel(new FlowLayout());
        logoPanel.setBackground(PRIMARY_TEAL);

        ImageIcon logoIcon = ImageUtils.loadImageIcon("/images/literanusa-logo.jpeg", 150, 150);
        JLabel logoLabel;
        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon);
        } else {
            logoLabel = new JLabel("📚 LiteraNusa", SwingConstants.CENTER);
            logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
            logoLabel.setForeground(SECONDARY_TEAL);
        }

        logoPanel.add(logoLabel);

        // Title section
        JPanel titlePanel = new JPanel(new GridLayout(3, 1));
        titlePanel.setBackground(PRIMARY_TEAL);

        JLabel titleLabel = new JLabel("LiteraNusa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(LIGHT_GRAY);

        JLabel subtitleLabel = new JLabel("Sistem Perpustakaan Digital", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(LIGHT_GRAY);

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        loadingLabel.setForeground(LIGHT_GRAY);

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        titlePanel.add(loadingLabel);

        // Progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(PRIMARY_TEAL);
        progressBar.setForeground(SECONDARY_TEAL);

        panel.add(logoPanel, BorderLayout.NORTH);
        panel.add(titlePanel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);

        add(panel);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void showSplashScreen() {
        setVisible(true);

        // Simulate loading time
        Timer timer = new Timer(3000, e -> {
            setVisible(false);
            dispose();
            // Show login screen
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        });
        timer.setRepeats(false);
        timer.start();
    }
}
