package com.literanusa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/literanusa_db";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        try {
            Statement stmt = connection.createStatement();

            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "full_name VARCHAR(100) NOT NULL," +
                    "role ENUM('USER', 'ADMIN') DEFAULT 'USER'," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create books table
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255) NOT NULL," +
                    "author VARCHAR(255) NOT NULL," +
                    "isbn VARCHAR(20) UNIQUE," +
                    "genre VARCHAR(100)," +
                    "synopsis TEXT," +
                    "rating DECIMAL(3,2) DEFAULT 0.00," +
                    "available_copies INT DEFAULT 1," +
                    "total_copies INT DEFAULT 1," +
                    "cover_image VARCHAR(255)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create loans table
            stmt.execute("CREATE TABLE IF NOT EXISTS loans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "book_id INT," +
                    "loan_date DATE NOT NULL," +
                    "due_date DATE NOT NULL," +
                    "return_date DATE," +
                    "status ENUM('ACTIVE', 'RETURNED', 'OVERDUE') DEFAULT 'ACTIVE'," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (book_id) REFERENCES books(id))");

            // Insert default admin user
            stmt.execute("INSERT IGNORE INTO users (username, password, email, full_name, role) " +
                    "VALUES ('admin', 'admin123', 'admin@literanusa.com', 'Administrator', 'ADMIN')");

            // Insert sample books
            stmt.execute("INSERT IGNORE INTO books (title, author, isbn, genre, synopsis, rating, available_copies, total_copies) VALUES " +
                    "('Laskar Pelangi', 'Andrea Hirata', '9789792248074', 'Drama', 'Novel tentang perjuangan anak-anak Belitung untuk mendapatkan pendidikan.', 4.5, 3, 3)," +
                    "('Bumi Manusia', 'Pramoedya Ananta Toer', '9789799731240', 'Sejarah', 'Novel sejarah tentang kehidupan di masa kolonial Belanda.', 4.8, 2, 2)," +
                    "('Ayat-Ayat Cinta', 'Habiburrahman El Shirazy', '9789792248081', 'Religi', 'Novel religi tentang cinta dan kehidupan seorang mahasiswa Indonesia di Mesir.', 4.3, 4, 4)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
