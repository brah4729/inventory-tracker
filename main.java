
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Tracker Menu:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Search Item");
            System.out.println("4. Store Inventory to File");
            System.out.println("5. Display Inventory");
            System.out.println("6. Load Inventory from File");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            InventoryTracker.displayInventory();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                case "addItem":
                case "add":
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    try {
                        if (name.trim().isEmpty()) {
                            System.out.println("Item name cannot be empty. Please enter a valid name.");
                            break;
                        }
                        if (name.matches(".*[^a-zA-Z0-9 ].*")) {
                            System.out.println("Item name contains invalid characters. Please enter a valid name.");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred while reading the item name. Please try again.");
                        break;
                    }
                    InventoryTracker.Item.Category category = null;
                    System.out.print("Enter item category (BOOKS, STATIONERY, ELECTRONICS): ");
                    for (int i = 0; i < 3; i++) {
                        String categoryInput = scanner.nextLine();
                        try {
                            category = InventoryTracker.Item.Category.valueOf(categoryInput.trim().toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            if (i < 2) {
                                System.out.println("Invalid category. Please enter a valid category.");
                            } else {
                                System.out.println("Invalid category entered multiple times. Returning to main menu.");
                            }
                        }
                    }
                    if (category == null) {
                        break; // Exit the add item process if category is invalid
                    }
                    System.out.print("Enter item quantity: ");
                    try {
                        int quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity <= 0) {
                            System.out.println("Quantity cannot be negative or zero. Please enter a valid quantity.");
                            break;
                        }
                        InventoryTracker.addItem(name, category, quantity);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity. Please enter a valid integer.");
                    }
                    break;
                case "2":
                case "removeItem":
                case "remove":
                    System.out.print("Enter item name to remove: ");
                    String removeName = scanner.nextLine();
                    try {
                        if (removeName.matches(".*[^a-zA-Z0-9 ].*")) {
                            System.out.println("Invalid input detected. Returning to main menu.");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred while removing the item. Please try again.");
                    }
                    InventoryTracker.removeItem(removeName);
                    break;
                case "3":
                case "searchItem":
                case "search":
                    System.out.print("Enter the name of the item to search: ");
                    String searchItem = scanner.nextLine();
                    InventoryTracker.search(searchItem);
                    try {
                        if (searchItem.matches(".*[^a-zA-Z0-9 ].*")) {
                            System.out.println("Invalid input detected. Returning to main menu.");
                            break;
                        }
                    } catch (Exception e) {
                        System.err.println("An error occurred while searching for the item. Please try again.");
                    }
                    break;
                case "4":
                case "storeInventory":
                case "store":
                    InventoryTracker.store();
                    break;
                case "5":
                case "displayInventory":
                case "display":
                    InventoryTracker.displayInventory();
                    break;
                case "6":
                case "loadInventory":
                case "load":
                    InventoryTracker.load();
                    try {
                        if (args.length == 0 || args[0].trim().isEmpty()) {
                            System.out.println("No file found to load.");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred while loading the inventory. Please try again.");
                    }
                    break;
                case "7":
                case "exit":
                    System.out.println("Exiting Inventory Tracker. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
