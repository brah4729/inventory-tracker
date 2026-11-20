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
                    System.out.print("Enter item category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    InventoryTracker.addItem(name, InventoryTracker.Item.Category.valueOf(category.toUpperCase()), quantity);
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