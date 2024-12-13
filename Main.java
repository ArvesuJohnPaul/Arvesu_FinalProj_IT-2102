package Final_proj_java;

import java.io.*;
import java.util.*;


public class Main {
    private static final String DATA_FILE = "C:\\Users\\arves\\Documents\\CODES\\StorageManagementSystem\\store_data.json";
    private Map<String, Item> storeData;
    private Scanner scanner;

    public Main() {
        scanner = new Scanner(System.in);
        storeData = loadStoreData();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    void start() {

        listItems();

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    listItems();
                    break;
                case 3:
                    editItem();
                    break;
                case 4:
                    deleteItem();
                    break;
                case 5:
                    saveStoreData();
                    System.out.println("Exiting... Data saved.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("1. Add Item");
        System.out.println("2. List Items");
        System.out.println("3. Edit Item");
        System.out.println("4. Remove Item");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addItem() {
        String name;
        while (true) {
            System.out.print("Enter Item Name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                if (storeData.containsKey(name)) {
                    System.out.println("Item already exists. Please choose a different name.");
                    continue;
                }
                break;
            }
            System.out.println("Item name cannot be empty. Please try again.");
        }
        
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        
        double price = 0;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(scanner.nextLine());
                if (price >= 0) {
                    break;
                }
                System.out.println("Price must be a non-negative number. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }
        
        int quantity = 0;
        while (true) {
            try {
                System.out.print("Enter Quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity >= 0) {
                    break;
                }
                System.out.println("Quantity must be a non-negative number. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a valid number.");
            }
        }
    
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
    
        storeData.put(name, new Item(name, description, price, quantity, category));
        System.out.println("Item added successfully!");
    }

    private void listItems() {
        if (storeData.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\n============================================= STORE ITEMS =============================================");
        
        List<Item> itemList = new ArrayList<>(storeData.values());
        for (int i = 0; i < itemList.size(); i += 4) {

            for (int j = 0; j < 4 && (i + j) < itemList.size(); j++) {
                System.out.printf("%-24s %s", "--------------------", j < 3 ? "|" : "");
            }
            System.out.println(); 
            

            for (int j = 0; j < 4 && (i + j) < itemList.size(); j++) {
                Item item = itemList.get(i + j);
                System.out.printf("%-24s %s", 
                    String.format("%s ($%.2f)", item.getName(), item.getPrice()), 
                    j < 3 ? "|" : ""
                );
            }
            System.out.println(); 
            
            for (int j = 0; j < 4 && (i + j) < itemList.size(); j++) {
                Item item = itemList.get(i + j);
                System.out.printf("%-24s %s", 
                    String.format("Qty: %d ", item.getQuantity()), 
                    j < 3 ? "|" : ""
                );
            }
            System.out.println(); 

            for (int j = 0; j < 4 && (i + j) < itemList.size(); j++) {
                Item item = itemList.get(i + j);
                System.out.printf("%-24s %s", 
                    String.format("Category: %s", item.getCategory()),
                    j < 3 ? "|" : ""
                );
            }
            System.out.println("\n"); 
        }

        System.out.println("===================================================================================================");
    }

    private void editItem() {
        System.out.print("Enter item name to edit: ");
        String name = scanner.nextLine();

        if (!storeData.containsKey(name)) {
            System.out.println("Item not found.");
            return;
        }

        Item item = storeData.get(name);

        System.out.print("Enter new description (press enter to skip): ");
        String description = scanner.nextLine();
        if (!description.trim().isEmpty()) {
            item.setDescription(description);
        }

        System.out.print("Enter new price (0 to skip): ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        if (price > 0) {
            item.setPrice(price);
        }

        System.out.print("Enter new quantity (-1 to skip): ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        if (quantity >= 0) {
            item.setQuantity(quantity);
        }

        System.out.print("Enter new category (press enter to skip): ");
        String category = scanner.nextLine();
        if (!category.trim().isEmpty()) {
            item.setCategory(category);
        }

        System.out.println("Item updated successfully.");
    }

    private void deleteItem() {
        System.out.print("Enter item name to remove: ");
        String name = scanner.nextLine();

        if (storeData.remove(name) != null) {
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private Map<String, Item> loadStoreData() {
        Map<String, Item> data = new HashMap<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 5) {
                    data.put(parts[0], new Item(parts[0], parts[1], Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]), parts[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }

        return data;
    }

    private void saveStoreData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Item item : storeData.values()) {
                writer.write(item.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    private static class Item {
        private String name;
        private String description;
        private double price;
        private int quantity;
        private String category;

        public Item(String name, String description, double price, int quantity, String category) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
        }

        // Getters
        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public String getCategory() { return category; }

        // Setters
        public void setDescription(String description) { this.description = description; }
        public void setPrice(double price) { this.price = price; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public void setCategory(String category) { this.category = category; }

        @Override
        public String toString() {
            return String.format("Item: %s\nDescription: %s\nPrice: ₱%.2f\nQuantity: %d\nCategory: %s", 
                    name, description, price, quantity, category);
        }

        public String toFileString() {
            return String.join(";", name, description, String.valueOf(price), String.valueOf(quantity), category);
        }
    }
}