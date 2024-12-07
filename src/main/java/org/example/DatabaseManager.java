package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://host.docker.internal:3306/password_manager";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to get database connection
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to save password to database
    public void savePassword(PasswordEntry entry) throws Exception {
        String sql = "INSERT INTO passwords (website, username, password) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entry.getWebsite());
            stmt.setString(2, entry.getUsername());
            stmt.setString(3, entry.getPassword());
            stmt.executeUpdate();
        }
    }

    // Method to retrieve password from database
    public PasswordEntry getPassword(String website) throws Exception {
        String sql = "SELECT * FROM passwords WHERE website = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, website);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PasswordEntry(
                        rs.getString("website"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        }
        return null;
    }
}