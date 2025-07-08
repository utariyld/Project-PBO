package com.literanusa.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageUtils {
    
    public static ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            URL imageURL = ImageUtils.class.getResource(path);
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                if (width > 0 && height > 0) {
                    Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
                return icon;
            }
        } catch (Exception e) {
            System.out.println("Could not load image: " + path);
        }
        
        // Return a placeholder icon if image not found
        return createPlaceholderIcon(width, height, path);
    }
    
    private static ImageIcon createPlaceholderIcon(int width, int height, String originalPath) {
        if (width <= 0) width = 16;
        if (height <= 0) height = 16;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create different icons based on the path
        if (originalPath.contains("home") || originalPath.contains("house")) {
            drawHomeIcon(g2d, width, height);
        } else if (originalPath.contains("catalog") || originalPath.contains("book")) {
            drawBookIcon(g2d, width, height);
        } else if (originalPath.contains("loan") || originalPath.contains("borrow")) {
            drawLoanIcon(g2d, width, height);
        } else if (originalPath.contains("user") || originalPath.contains("profile")) {
            drawUserIcon(g2d, width, height);
        } else if (originalPath.contains("search")) {
            drawSearchIcon(g2d, width, height);
        } else if (originalPath.contains("star") || originalPath.contains("rating")) {
            drawStarIcon(g2d, width, height);
        } else if (originalPath.contains("heart") || originalPath.contains("wishlist")) {
            drawHeartIcon(g2d, width, height);
        } else if (originalPath.contains("available")) {
            drawCheckIcon(g2d, width, height);
        } else if (originalPath.contains("unavailable")) {
            drawXIcon(g2d, width, height);
        } else if (originalPath.contains("category")) {
            drawCategoryIcon(g2d, width, height);
        } else if (originalPath.contains("popular")) {
            drawTrophyIcon(g2d, width, height);
        } else {
            drawDefaultIcon(g2d, width, height);
        }
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    private static void drawHomeIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        g2d.setStroke(new BasicStroke(2f));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // House shape
        int[] xPoints = {x + w/2, x, x + w/4, x + 3*w/4, x + w};
        int[] yPoints = {y, y + h/3, y + h, y + h, y + h/3};
        g2d.fillPolygon(xPoints, yPoints, 5);
        
        // Door
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + w/2 - w/8, y + 2*h/3, w/4, h/3);
    }
    
    private static void drawBookIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(72, 201, 176));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Book cover
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, w, h);
        
        // Pages
        g2d.setColor(new Color(95, 158, 160));
        for (int i = 1; i <= 3; i++) {
            g2d.drawLine(x + i * w/5, y + h/4, x + i * w/5, y + 3*h/4);
        }
    }
    
    private static void drawLoanIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        g2d.setStroke(new BasicStroke(2f));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Arrow pointing right
        g2d.drawLine(x, y + h/2, x + w - 4, y + h/2);
        g2d.drawLine(x + w - 6, y + h/4, x + w - 2, y + h/2);
        g2d.drawLine(x + w - 6, y + 3*h/4, x + w - 2, y + h/2);
    }
    
    private static void drawUserIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Head
        g2d.fillOval(x + w/4, y, w/2, h/2);
        
        // Body
        g2d.fillOval(x, y + h/2, w, h/2);
    }
    
    private static void drawSearchIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        g2d.setStroke(new BasicStroke(2f));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Magnifying glass
        g2d.drawOval(x, y, w - 4, h - 4);
        g2d.drawLine(x + w - 6, y + h - 6, x + w - 2, y + h - 2);
    }
    
    private static void drawStarIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.ORANGE);
        
        int margin = 2;
        int cx = width / 2;
        int cy = height / 2;
        int r = Math.min(width, height) / 2 - margin;
        
        // Simple star shape
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI * i / 5;
            int radius = (i % 2 == 0) ? r : r / 2;
            xPoints[i] = (int) (cx + radius * Math.cos(angle - Math.PI / 2));
            yPoints[i] = (int) (cy + radius * Math.sin(angle - Math.PI / 2));
        }
        
        g2d.fillPolygon(xPoints, yPoints, 10);
    }
    
    private static void drawHeartIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(255, 105, 180));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Simple heart shape using two circles and a triangle
        g2d.fillOval(x, y, w/2, h/2);
        g2d.fillOval(x + w/2, y, w/2, h/2);
        
        int[] xPoints = {x, x + w, x + w/2};
        int[] yPoints = {y + h/3, y + h/3, y + h};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    
    private static void drawCheckIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(40, 167, 69));
        g2d.setStroke(new BasicStroke(2f));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Check mark
        g2d.drawLine(x + w/4, y + h/2, x + w/2, y + 3*h/4);
        g2d.drawLine(x + w/2, y + 3*h/4, x + 3*w/4, y + h/4);
    }
    
    private static void drawXIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(220, 53, 69));
        g2d.setStroke(new BasicStroke(2f));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // X mark
        g2d.drawLine(x, y, x + w, y + h);
        g2d.drawLine(x + w, y, x, y + h);
    }
    
    private static void drawCategoryIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Grid of squares
        int squareSize = w / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.fillRect(x + i * squareSize, y + j * squareSize, squareSize - 1, squareSize - 1);
                }
            }
        }
    }
    
    private static void drawTrophyIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.ORANGE);
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Trophy cup
        g2d.fillRect(x + w/4, y, w/2, 2*h/3);
        
        // Base
        g2d.fillRect(x, y + 2*h/3, w, h/3);
        
        // Handles
        g2d.drawArc(x - w/8, y + h/8, w/4, h/4, -90, 180);
        g2d.drawArc(x + 7*w/8, y + h/8, w/4, h/4, 90, 180);
    }
    
    private static void drawDefaultIcon(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(95, 158, 160));
        
        int margin = 2;
        int x = margin;
        int y = margin;
        int w = width - 2 * margin;
        int h = height - 2 * margin;
        
        // Simple rectangle with question mark
        g2d.drawRect(x, y, w, h);
        g2d.setFont(new Font("Arial", Font.BOLD, Math.min(width, height) / 2));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "?";
        int textX = x + (w - fm.stringWidth(text)) / 2;
        int textY = y + (h + fm.getAscent()) / 2;
        g2d.drawString(text, textX, textY);
    }
    
    public static JLabel createBookCoverLabel(String coverImage, int width, int height) {
        String imagePath = "/images/covers/" + (coverImage != null ? coverImage : "default-book-cover.jpeg");
        ImageIcon icon = loadImageIcon(imagePath, width, height);
        
        if (icon == null) {
            // Create a book placeholder
            BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = placeholder.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background
            g2d.setColor(new Color(248, 249, 250));
            g2d.fillRect(0, 0, width, height);
            
            // Border
            g2d.setColor(new Color(222, 226, 230));
            g2d.drawRect(0, 0, width - 1, height - 1);
            
            // Book icon
            g2d.setColor(new Color(95, 158, 160));
            drawBookIcon(g2d, width, height);
            
            g2d.dispose();
            icon = new ImageIcon(placeholder);
        }
        
        return new JLabel(icon);
    }

    // Method untuk resize image dengan kualitas tinggi
    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Method untuk membuat gradient background
    public static void paintGradientBackground(Graphics g, Component component, Color color1, Color color2) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        GradientPaint gradient = new GradientPaint(
            0, 0, color1,
            component.getWidth(), component.getHeight(), color2
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, component.getWidth(), component.getHeight());
    }

    // Method untuk membuat shadow effect
    public static void paintShadow(Graphics2D g2d, int x, int y, int width, int height, int shadowSize) {
        Color shadowColor = new Color(0, 0, 0, 20);
        for (int i = 0; i < shadowSize; i++) {
            g2d.setColor(new Color(0, 0, 0, 20 - i * 2));
            g2d.drawRoundRect(x + i, y + i, width - 2*i, height - 2*i, 5, 5);
        }
    }
}
