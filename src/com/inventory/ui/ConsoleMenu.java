package com.inventory.ui;

import com.inventory.exception.OutOfStockException;
import com.inventory.model.Category;
import com.inventory.model.Item;
import com.inventory.service.InventoryService;

import java.util.List;
import java.util.Scanner;

/** Handles input and output only. All rules are delegated to the service. */
public class ConsoleMenu {
    private static final int LOW_STOCK_THRESHOLD = 5;

    private final InventoryService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(InventoryService service) {
        this.service = service;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addItem();
                    case "2" -> listItems(service.getAllItems());
                    case "3" -> addStock();
                    case "4" -> removeStock();
                    case "5" -> searchItems();
                    case "6" -> listItems(service.getLowStockItems(LOW_STOCK_THRESHOLD));
                    case "7" -> deleteItem();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (OutOfStockException e) {
                System.out.println("Stock error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n=== Inventory Tracker ===");
        System.out.println("1. Add item");
        System.out.println("2. List all items");
        System.out.println("3. Add stock");
        System.out.println("4. Remove stock");
        System.out.println("5. Search by name");
        System.out.println("6. Low-stock report (<= " + LOW_STOCK_THRESHOLD + ")");
        System.out.println("7. Delete item");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private void addItem() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        int quantity = readInt("Quantity: ");
        double price = readDouble("Price: ");
        Category category = readCategory();
        Item item = service.addItem(name, quantity, price, category);
        System.out.println("Added: " + item);
    }

    private void addStock() {
        int id = readInt("Item id: ");
        int amount = readInt("Amount to add: ");
        service.addStock(id, amount);
        System.out.println("Stock updated.");
    }

    private void removeStock() throws OutOfStockException {
        int id = readInt("Item id: ");
        int amount = readInt("Amount to remove: ");
        service.removeStock(id, amount);
        System.out.println("Stock updated.");
    }

    private void searchItems() {
        System.out.print("Keyword: ");
        listItems(service.searchByName(scanner.nextLine()));
    }

    private void deleteItem() {
        int id = readInt("Item id to delete: ");
        service.deleteItem(id);
        System.out.println("Item deleted.");
    }

    private void listItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("(no items)");
            return;
        }
        for (Item item : items) {
            System.out.println(item);
        }
    }

    // ---- input helpers: keep retrying until the user types a valid value ----

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (e.g. 9.99).");
            }
        }
    }

    private Category readCategory() {
        Category[] values = Category.values();
        System.out.println("Categories:");
        for (int i = 0; i < values.length; i++) {
            System.out.println("  " + (i + 1) + ". " + values[i]);
        }
        while (true) {
            int choice = readInt("Pick category number: ");
            if (choice >= 1 && choice <= values.length) {
                return values[choice - 1];
            }
            System.out.println("Out of range.");
        }
    }
}
