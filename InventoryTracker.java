
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryTracker {

    public static class Item {

        String name;

        public enum Category {
            BOOKS, STATIONERY, ELECTRONICS
        }
        Category category;
        int quantity;

        public Item(String name, Item.Category category, int quantity) {
            this.name = name;
            this.category = category;
            this.quantity = quantity;
        }
    }

    static ArrayList<Item> inventory = new ArrayList<>();

    //load inventory from a local file storage
    public static void load() {
        try {
            Scanner scanner = new Scanner(new java.io.File("inventory.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String category = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    inventory.add(new Item(name, Item.Category.valueOf(category.toUpperCase()), quantity));
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

    //add item and quantity to an existing item in the inventory
    public static void addItem(String name, Item.Category category, int quantity) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(name)) {
                inventory.get(i).quantity += quantity;
                inventory.get(i).category = category;
                System.out.println("Updated quantity of " + name + " to " + inventory.get(i).quantity);
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

    public static void search(String searchItem) {

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(searchItem)) {
                System.out.println("Item found: " + inventory.get(i).name + ", Category: " + inventory.get(i).category + ", Quantity: " + inventory.get(i).quantity);
                return;
            }
        }
        System.out.println("Item not found: " + searchItem);
    }

    //store all of that in a local file storage 
    public static void store() {
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
}
