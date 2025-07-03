package com.literanusa.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.File;

public class ImageUtils {

    // Main method untuk load image dengan resize
    public static ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = null;

            // First try to load from resources
            InputStream is = ImageUtils.class.getResourceAsStream(path);
            if (is != null) {
                originalImage = ImageIO.read(is);
            } else {
                // If not found in resources, try to load from file system
                File file = new File(path);
                if (file.exists()) {
                    originalImage = ImageIO.read(file);
                }
            }

            if (originalImage != null) {
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }

        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        return null;
    }

    // Method untuk load image tanpa resize
    public static ImageIcon loadImageIcon(String path) {
        try {
            InputStream is = ImageUtils.class.getResourceAsStream(path);
            if (is != null) {
                BufferedImage originalImage = ImageIO.read(is);
                return new ImageIcon(originalImage);
            } else {
                // Try file system
                File file = new File(path);
                if (file.exists()) {
                    BufferedImage originalImage = ImageIO.read(file);
                    return new ImageIcon(originalImage);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        return null;
    }

    public static JLabel createImageLabel(String imagePath, int width, int height) {
        ImageIcon icon = loadImageIcon(imagePath, width, height);
        if (icon != null) {
            return new JLabel(icon);
        } else {
            return new JLabel("Image not found: " + imagePath);
        }
    }

    public static JPanel createHeaderPanel(String headerImagePath, int height) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, height));

        ImageIcon headerIcon = loadImageIcon(headerImagePath);
        if (headerIcon != null) {
            JLabel headerLabel = new JLabel(headerIcon);
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
        } else {
            headerPanel.setBackground(new Color(139, 69, 19));
            JLabel fallbackLabel = new JLabel("LiteraNusa Header", SwingConstants.CENTER);
            fallbackLabel.setForeground(Color.WHITE);
            headerPanel.add(fallbackLabel, BorderLayout.CENTER);
        }

        return headerPanel;
    }

    public static ImageIcon loadBookCover(String coverImageName, int width, int height) {
        String imagePath = "/images/covers/" + (coverImageName != null ? coverImageName : "default-book-cover.jpg");
        ImageIcon icon = loadImageIcon(imagePath, width, height);

        // If specific cover not found, try default
        if (icon == null) {
            icon = loadImageIcon("/images/covers/default-book-cover.jpg", width, height);
        }

        return icon;
    }

    public static JLabel createBookCoverLabel(String coverImageName, int width, int height) {
        ImageIcon coverIcon = loadBookCover(coverImageName, width, height);

        if (coverIcon != null) {
            JLabel coverLabel = new JLabel(coverIcon);
            coverLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)
            ));
            coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
            return coverLabel;
        } else {
            // Enhanced fallback design
            JLabel fallbackLabel = new JLabel();
            fallbackLabel.setPreferredSize(new Dimension(width, height));
            fallbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
            fallbackLabel.setVerticalAlignment(SwingConstants.CENTER);

            // Create a gradient background
            fallbackLabel.setOpaque(true);
            fallbackLabel.setBackground(new Color(245, 245, 220));
            fallbackLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2, 2, 4, 4),
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1)
            ));

            // Add book icon
            fallbackLabel.setText("<html><div style='text-align: center;'>" +
                    "<div style='font-size: 48px; color: #CD853F;'>📚</div>" +
                    "<div style='font-size: 12px; color: #8B4513; margin-top: 10px;'>Cover<br>Not Available</div>" +
                    "</div></html>");

            return fallbackLabel;
        }
    }

    // Method untuk membuat card dengan shadow effect
    public static JPanel createShadowPanel(JPanel content) {
        JPanel shadowPanel = new JPanel();
        shadowPanel.setLayout(new BorderLayout());
        shadowPanel.setOpaque(false);

        // Add shadow effect
        shadowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 4, 4),
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1)
        ));

        shadowPanel.add(content, BorderLayout.CENTER);
        return shadowPanel;
    }

    // Method untuk resize image dengan kualitas tinggi
    public static BufferedImage resizeImageHighQuality(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // Set rendering hints untuk kualitas tinggi
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    // Add method for creating circular profile pictures
    public static ImageIcon createCircularProfilePicture(String imagePath, int size) {
        try {
            BufferedImage originalImage = null;

            // Try to load from resources first
            InputStream is = ImageUtils.class.getResourceAsStream(imagePath);
            if (is != null) {
                originalImage = ImageIO.read(is);
            } else {
                // Try to load from file system
                File file = new File(imagePath);
                if (file.exists()) {
                    originalImage = ImageIO.read(file);
                }
            }

            if (originalImage == null) {
                return null;
            }

            // Create circular image
            BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = circularImage.createGraphics();

            // Enable anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create circular clip
            g2d.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));

            // Draw the image
            g2d.drawImage(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();

            return new ImageIcon(circularImage);

        } catch (IOException e) {
            System.err.println("Error creating circular profile picture: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }
}
