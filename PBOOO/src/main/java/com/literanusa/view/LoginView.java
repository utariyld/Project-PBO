package com.literanusa.view;

import com.literanusa.controller.AuthController;
import com.literanusa.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.literanusa.util.ImageUtils;

public class LoginView extends JFrame {
    private AuthController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;

    // Updated Color scheme with Teal palette
    private final Color PRIMARY_TEAL = new Color(95, 158, 160);
    private final Color SECONDARY_TEAL = new Color(72, 201, 176);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    private final Color WHITE = Color.WHITE;
    private final Color DARK_TEAL = new Color(47, 79, 79); // Darker teal for text
    private final Color ACCENT_TEAL = new Color(176, 224, 230); // Light teal for accents

    public LoginView() {
        this.authController = new AuthController();
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initializeComponents() {
        setTitle("LiteraNusa - Sistem Perpustakaan Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_GRAY);

        // Header Panel with Logo
        JPanel headerPanel = createHeaderPanel();

        // Login Form Panel
        JPanel loginPanel = createLoginPanel();

        // Footer Panel
        JPanel footerPanel = createFooterPanel();

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                ImageIcon bgIcon = ImageUtils.loadImageIcon("/images/literanusa-header.jpeg", -1, -1);
                if (bgIcon != null) {
                    backgroundImage = bgIcon.getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(PRIMARY_TEAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        headerPanel.setPreferredSize(new Dimension(0, 120));
        headerPanel.setOpaque(false);

        // Konten: logo + teks horizontal
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPanel.setOpaque(false);

        ImageIcon logoIcon = ImageUtils.loadImageIcon("/images/literanusa-logo.jpeg", 80, 80);
        JLabel logoLabel = (logoIcon != null)
                ? new JLabel(logoIcon)
                : new JLabel("📚", SwingConstants.CENTER);

        JLabel titleLabel = new JLabel("Sistem Perpustakaan Digital");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(WHITE);

        contentPanel.add(logoLabel);
        contentPanel.add(titleLabel);

        headerPanel.add(contentPanel, BorderLayout.WEST);

        return headerPanel;
    }



    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel loginPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        loginPanel.setBackground(WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 2),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.insets = new Insets(15, 15, 15, 15);

        // Title with modern styling
        JLabel titleLabel = new JLabel("Masuk ke LiteraNusa");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(DARK_TEAL);
        loginGbc.gridx = 0; loginGbc.gridy = 0;
        loginGbc.gridwidth = 2;
        loginGbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, loginGbc);

        // Reset insets
        loginGbc.insets = new Insets(10, 15, 10, 15);
        loginGbc.gridwidth = 1;
        loginGbc.anchor = GridBagConstraints.WEST;

        // Username with modern label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(DARK_TEAL);
        loginGbc.gridx = 0; loginGbc.gridy = 2;
        loginPanel.add(usernameLabel, loginGbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        usernameField.setBackground(LIGHT_GRAY);
        loginGbc.gridx = 1; loginGbc.gridy = 2;
        loginPanel.add(usernameField, loginGbc);

        // Password with modern label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(DARK_TEAL);
        loginGbc.gridx = 0; loginGbc.gridy = 3;
        loginPanel.add(passwordLabel, loginGbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        passwordField.setBackground(LIGHT_GRAY);
        loginGbc.gridx = 1; loginGbc.gridy = 3;
        loginPanel.add(passwordField, loginGbc);

        // Buttons with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(WHITE);

        loginButton = createStyledButton("Masuk", SECONDARY_TEAL, WHITE, true);
        registerButton = createStyledButton("Daftar", PRIMARY_TEAL, WHITE, false);
        forgotPasswordButton = createStyledButton("Lupa Password?", LIGHT_GRAY, DARK_TEAL, false);
        forgotPasswordButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginGbc.gridx = 0; loginGbc.gridy = 4;
        loginGbc.gridwidth = 2;
        loginGbc.anchor = GridBagConstraints.CENTER;
        loginGbc.insets = new Insets(20, 15, 10, 15);
        loginPanel.add(buttonPanel, loginGbc);

        // Forgot password button separately
        loginGbc.gridx = 0; loginGbc.gridy = 5;
        loginGbc.gridwidth = 2;
        loginGbc.insets = new Insets(5, 15, 0, 15);
        loginPanel.add(forgotPasswordButton, loginGbc);

        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginPanel, gbc);

        return mainPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Draw text
                g2d.setColor(textColor);
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
            }
        };

        button.setPreferredSize(new Dimension(isPrimary ? 120 : 100, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, SECONDARY_TEAL, 0, getHeight(), PRIMARY_TEAL);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        footerPanel.setPreferredSize(new Dimension(0, 50));

        JLabel footerLabel = new JLabel("© 2024 LiteraNusa - Sistem Perpustakaan Digital | Follow us: Facebook | Twitter | Instagram");
        footerLabel.setForeground(WHITE);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private void setupLayout() {
        // Layout is already set up in initializeComponents
    }

    private void setupEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginView.this,
                        "Fitur lupa password akan segera tersedia.\nSilakan hubungi administrator.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Enter key for login
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = authController.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + user.getFullName(), "Success", JOptionPane.INFORMATION_MESSAGE);

            // Open appropriate dashboard based on role
            if (user.getRole() == User.Role.ADMIN) {
                new AdminDashboardView(user).setVisible(true);
            } else {
                new UserDashboardView(user).setVisible(true);
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "Daftar Akun Baru", true);
        registerDialog.setSize(450, 400);
        registerDialog.setLocationRelativeTo(this);
        registerDialog.getContentPane().setBackground(LIGHT_GRAY);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Daftar Akun Baru");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(DARK_TEAL);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Form fields
        JTextField regUsernameField = createStyledTextField();
        JPasswordField regPasswordField = createStyledPasswordField();
        JTextField regEmailField = createStyledTextField();
        JTextField regFullNameField = createStyledTextField();

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Add form fields with labels
        addFormField(panel, gbc, "Username:", regUsernameField, 1);
        addFormField(panel, gbc, "Password:", regPasswordField, 2);
        addFormField(panel, gbc, "Email:", regEmailField, 3);
        addFormField(panel, gbc, "Nama Lengkap:", regFullNameField, 4);

        JButton submitButton = createStyledButton("Daftar", SECONDARY_TEAL, WHITE, true);
        submitButton.setPreferredSize(new Dimension(150, 40));

        submitButton.addActionListener(e -> {
            String username = regUsernameField.getText().trim();
            String password = new String(regPasswordField.getPassword());
            String email = regEmailField.getText().trim();
            String fullName = regFullNameField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (authController.register(username, password, email, fullName)) {
                JOptionPane.showMessageDialog(registerDialog, "Registrasi berhasil! Silakan login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                registerDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(registerDialog, "Registrasi gagal! Username mungkin sudah digunakan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(submitButton, gbc);

        // Wrap panel in main container
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBackground(LIGHT_GRAY);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainContainer.add(panel, mainGbc);

        registerDialog.add(mainContainer);
        registerDialog.setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(LIGHT_GRAY);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_TEAL, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(LIGHT_GRAY);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(DARK_TEAL);

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}
