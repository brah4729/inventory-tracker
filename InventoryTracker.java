import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class InventoryTracker {
    static class Item {

        String name;
        String category;
        int quantity;

        public Item(String name, String category, int quantity) {
            this.name = name;
            this.category = category;
            this.quantity = quantity;
        }
    }
    
        static ArrayList<Item> inventory = new ArrayList<>();

        //load inventory from a local file storage
    public static void load(String[] args) {
        try {
            Scanner scanner = new Scanner(new java.io.File("inventory.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String category = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    inventory.add(new Item(name, category, quantity));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error occurred while reading from file.");
        }
    }

    public static void displayInventory() {
        System.out.println("Current Inventory:");
        for (Item item : inventory) {
            System.out.println("Name: " + item.name + ", Category: " + item.category + ", Quantity: " + item.quantity);
        }
    }

    public static void atStartup(String[] args) {
        load(args);
        displayInventory();
    }

    //add item and quantity to an existing item in the inventory
    public static void addItem(String name, String category, int quantity) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(name)) {
                inventory.get(i).quantity += quantity;
                return;
            }
        }
        inventory.add(new Item(name, category, quantity));
    }
    //remove item from the inventory
    public static void removeItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(name)) {
                inventory.remove(i);
                System.out.println("Item removed: " + name);
                return;
            } 
        }
        System.out.println("Item not found: " + name);
    }
        // search for a specific item in the inventory

    public static void search() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the item to search: ");
        String searchItem = scanner.nextLine();
        for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).name.equalsIgnoreCase(searchItem)) {
                System.out.println("Item found: " + inventory.get(i).name + ", Category: " + inventory.get(i).category + ", Quantity: " + inventory.get(i).quantity);
                return;
            }
        }
        System.out.println("Item not found: " + searchItem);
    }

    //store all of that in a local file storage 
    public static void store(String[] args) {
        try {
            FileWriter writer = new FileWriter("inventory.txt");
            for (Item item : inventory) {
                writer.write(item.name + ", " + item.category + ", " + item.quantity + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing to file.");
        }
    }
    public static void main(String[] args) {
        atStartup(args);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Tracker Menu:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Search Item");
            System.out.println("4. Store Inventory to File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter item category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    addItem(name, category, quantity);
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    String removeName = scanner.nextLine();
                    removeItem(removeName);
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    store(args);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
