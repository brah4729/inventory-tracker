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
        static ArrayList<Item> inventory = new ArrayList();
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
                return;
            }
        }
    }
    //categories 
    public static void categories() {
        //implement categories for the inventory items

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
    }

    //store all of that in a local file storage 
    public static void store(String[] args) {
        
    }
}
