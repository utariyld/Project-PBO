package com.literanusa;

import com.literanusa.view.LoginView;
import com.literanusa.util.DatabaseConnection;
import com.literanusa.view.SplashScreen;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Initialize database connection
            DatabaseConnection.getInstance();

            SwingUtilities.invokeLater(() -> {
                // Show splash screen first
                new SplashScreen();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
