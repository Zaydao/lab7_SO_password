package org.example;

import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private List<PasswordEntry> passwordEntries;
    private DatabaseManager databaseManager;
    private static final String ENCRYPTION_KEY = "YourSecretKey123";

    // Constructor
    public PasswordManager() {
        this.passwordEntries = new ArrayList<>();
        this.databaseManager = new DatabaseManager();
    }

    // Method to add password
    public void addPassword(String website, String username, String password) {
        try {
            // Encrypt the password before storing
            String encryptedPassword = EncryptionUtil.encrypt(password, ENCRYPTION_KEY);

            PasswordEntry entry = new PasswordEntry(website, username, encryptedPassword);
            passwordEntries.add(entry);

            // Save to database
            databaseManager.savePassword(entry);

            System.out.println("Password for " + website + " added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding password: " + e.getMessage());
        }
    }

    // Method to view saved passwords
    public void viewPasswords() {
        if (passwordEntries.isEmpty()) {
            System.out.println("No passwords saved.");
        } else {
            for (PasswordEntry entry : passwordEntries) {
                System.out.println(entry);
            }
        }
    }

    // Method to retrieve a specific password
    public void retrievePassword(String website) {
        try {
            PasswordEntry entry = databaseManager.getPassword(website);
            if (entry != null) {
                String decryptedPassword = EncryptionUtil.decrypt(entry.getPassword(), ENCRYPTION_KEY);
                System.out.println("Website: " + entry.getWebsite());
                System.out.println("Username: " + entry.getUsername());
                System.out.println("Password: " + decryptedPassword);
            } else {
                System.out.println("No password found for " + website);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving password: " + e.getMessage());
        }
    }
}