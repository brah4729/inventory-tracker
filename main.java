
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
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                case "addItem":
                case "add":
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    InventoryTracker.Item.Category category = null;
                    System.out.print("Enter item category (BOOKS, STATIONERY, ELECTRONICS): ");
                    for (int i = 0; i < 3; i++) {
                        String categoryInput = scanner.nextLine();
                        try {
                            category = InventoryTracker.Item.Category.valueOf(categoryInput.toUpperCase());
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
                    InventoryTracker.removeItem(removeName);
                    break;
                case "3":
                case "searchItem":
                case "search":
                    System.out.print("Enter the name of the item to search: ");
                    String searchItem = scanner.nextLine();
                    InventoryTracker.search(searchItem);
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
                    InventoryTracker.load(args);
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
