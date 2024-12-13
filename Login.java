package Final_proj_java;

import java.io.*;
import java.util.*;

public class Login {

    private static final String ACCOUNTS_FILE = "C:\\Users\\arves\\Documents\\CODES\\StorageManagementSystem\\user_accounts.json";
    private Map<String, UserAccount> accounts;
    private Scanner scanner;

    public static void main(String[] args) {
        Login app = new Login();
        app.start();
    }

    public Login() {
        scanner = new Scanner(System.in);
        accounts = loadAccounts();
    }

    private void start() {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (login()) {
                        Main main = new Main();
                        main.start();
                    }
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Login System ---");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void createAccount() {
        String username;
        while (true) {
            System.out.print("Choose a username: ");
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            if (accounts.containsKey(username)) {
                System.out.println("Username already exists. Please choose another.");
                continue;
            }

            break;
        }

        String password;
        while (true) {
            System.out.print("Choose a password: ");
            password = scanner.nextLine();

            if (password.length() < 6) {
                System.out.println("Password must be at least 6 characters long.");
                continue;
            }

            System.out.print("Confirm password: ");
            String confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            }

            break;
        }

        // Optional: Additional profile information
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Create new user account
        UserAccount newUser = new UserAccount(username, password, fullName, email);
        accounts.put(username, newUser);

        // Save accounts to file
        saveAccounts();

        System.out.println("Account created successfully!");
    }

    private boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if username exists and password is correct
        if (accounts.containsKey(username)) {
            UserAccount user = accounts.get(username);
            if (verifyPassword(password, user.getPassword())) {
                System.out.println("Login successful!");
                return true;
            }
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }

    private Map<String, UserAccount> loadAccounts() {
        Map<String, UserAccount> loadedAccounts = new HashMap<>();
        File file = new File(ACCOUNTS_FILE);

        if (!file.exists()) {
            return loadedAccounts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UserAccount account = UserAccount.fromJsonString(line);
                if (account != null) {
                    loadedAccounts.put(account.getUsername(), account);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load accounts: " + e.getMessage());
        }

        return loadedAccounts;
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (UserAccount account : accounts.values()) {
                writer.write(account.toJsonString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save accounts: " + e.getMessage());
        }
    }

    private static class UserAccount {
        private String username;
        private String password;
        private String fullName;
        private String email;

        public UserAccount(String username, String password, String fullName, String email) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String toJsonString() {
            return String.format("{\"username\":\"%s\",\"password\":\"%s\",\"fullName\":\"%s\",\"email\":\"%s\"}",
                    username, password, fullName, email);
        }

        public static UserAccount fromJsonString(String jsonString) {
            try {
                jsonString = jsonString.replace("{", "").replace("}", "");
                String[] parts = jsonString.split(",");

                String username = extractValue(parts[0]);
                String password = extractValue(parts[1]);
                String fullName = extractValue(parts[2]);
                String email = extractValue(parts[3]);

                return new UserAccount(username, password, fullName, email);
            } catch (Exception e) {
                System.out.println("Error parsing account: " + jsonString);
                return null;
            }
        }

        private static String extractValue(String part) {
            return part.split(":")[1].replace("\"", "").trim();
        }
    }
}
