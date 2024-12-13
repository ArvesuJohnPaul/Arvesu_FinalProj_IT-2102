
# Storage Management System

## Overview
The **Storage Management System** is a Java-based application designed to help manage a store's inventory and user accounts. It provides functionality for managing items, such as adding, editing, listing, and deleting, and includes a user authentication system.

---

## Features

### 1. Login System (`Login.java`)
- **Create Account**: Users can create an account with a username, password, full name, and email.
- **User Authentication**: Secure login with username and password.
- **Account Data Management**: Stores user accounts in a JSON file (`user_accounts.json`).

### 2. Inventory Management (`Main.java`)
- **Add Items**: Add new items with details like name, description, price, quantity, and category.
- **Edit Items**: Modify item attributes.
- **List Items**: View items in a formatted grid layout.
- **Delete Items**: Remove items from the inventory.
- **Data Persistence**: Saves and loads item data to/from a JSON file (`store_data.json`).

---

## Requirements
- **Java Version**: 8 or higher
- **Libraries**: No external libraries are required. All dependencies are part of the Java Standard Library.
- **File System**:
  - `user_accounts.json`: Stores user account data.
  - `store_data.json`: Stores inventory data.

---

## How to Run

1. **Clone or Download** the project.
2. Open the project in your favorite IDE.
3. Ensure the files `user_accounts.json` and `store_data.json` are available in the specified directory:
   ```
   C:\Users\<your-user>\Documents\CODES\StorageManagementSystem\
   ```
4. Compile and run the `Login.java` file to start the application:
   ```
   javac Final_proj_java/Login.java
   java Final_proj_java.Login
   ```

---

## Usage

### Starting the Application
Run the `Login` program, where you can:
- Log in to the system.
- Create a new user account.
- Exit the application.

### After Logging In
You will be redirected to the **Inventory Management** system. Use the menu to manage your store's inventory:
1. Add new items.
2. View and manage existing inventory.
3. Edit item details.
4. Delete unwanted items.

---

## Future Improvements
- **GUI Implementation**: Add a graphical user interface for easier interaction.
- **Error Handling**: Improve error management for edge cases.
- **Reports**: Generate detailed inventory and user activity reports.

---
