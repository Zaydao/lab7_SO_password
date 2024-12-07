package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PasswordManager passwordManager = new PasswordManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Password Manager!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a new password");
            System.out.println("2. View saved passwords");
            System.out.println("3. Retrieve a password");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter the website name: ");
                        String website = scanner.nextLine();
                        System.out.print("Enter the username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter the password: ");
                        String password = scanner.nextLine();

                        passwordManager.addPassword(website, username, password);
                        break;

                    case 2:
                        passwordManager.viewPasswords();
                        break;

                    case 3:
                        System.out.print("Enter the website to retrieve password: ");
                        String retrieveWebsite = scanner.nextLine();
                        passwordManager.retrievePassword(retrieveWebsite);
                        break;

                    case 4:
                        System.out.println("Exiting Password Manager...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}