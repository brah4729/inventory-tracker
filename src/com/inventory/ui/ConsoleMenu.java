package com.inventory.ui;

import com.inventory.exception.OutOfStockException;
import com.inventory.model.Category;
import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import com.inventory.service.ItemSorter;
import com.inventory.service.ItemSorter.SortBy;
import com.inventory.service.SearchSuggestion;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Handles input and output only. All rules are delegated to the service.
 */
public class ConsoleMenu {

    private static final int LOW_STOCK_THRESHOLD = 5;
    private final SearchSuggestion suggestion = new SearchSuggestion();
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
                    case "1" ->
                        addItem();
                    case "2" ->
                        listAllItems();
                    case "3" ->
                        addStock();
                    case "4" ->
                        removeStock();
                    case "5" ->
                        searchItems();
                    case "6" ->
                        listItems(service.getLowStockItems(LOW_STOCK_THRESHOLD));
                    case "7" ->
                        deleteItem();
                    case "0" ->
                        running = false;
                    default ->
                        System.out.println("Invalid choice, try again.");
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
        Optional<String> name = readString("Name");
        if (name.isEmpty()) {
            return;
        }
        Optional<Integer> quantity = readInt("Quantity");
        if (quantity.isEmpty()) {
            return;
        }
        Optional<Double> price = readDouble("Price");
        if (price.isEmpty()) {
            return;
        }
        Optional<Category> category = readCategory("Pick category number");
        if (category.isEmpty()) {
            return;
        }
        Item item = service.addItem(name.get(), quantity.get(), price.get(), category.get());
        System.out.println("Added: " + item);
    }

    private void addStock() {
        Optional<Integer> id = readInt("Item id");
        if (id.isEmpty()) {
            return;
        }
        Optional<Integer> amount = readInt("Amount to add");
        if (amount.isEmpty()) {
            return;
        }
        service.addStock(id.get(), amount.get());
        System.out.println("Stock updated.");
    }

    private void removeStock() throws OutOfStockException {
        Optional<Integer> id = readInt("Item id");
        if (id.isEmpty()) {
            return;
        }
        Optional<Integer> amount = readInt("Amount to remove");
        if (amount.isEmpty()) {
            return;
        }
        service.removeStock(id.get(), amount.get());
        System.out.println("Stock updated.");
    }

    private void searchItems() {
        Optional<String> keyword = readString("Keyword");
        if (keyword.isEmpty()) {
            return;
        }
        
        List<Item> suggestions = suggestion.suggestItems(service.getAllItems(), keyword.get(), 5);
        listItems(suggestions);
        listItems(service.searchByName(keyword.get()));
    }

    private void deleteItem() {
        Optional<Integer> id = readInt("Item id to delete");
        if (id.isEmpty()) {
            return;
        }
        service.deleteItem(id.get());
        System.out.println("Item deleted.");
    }

    private void listAllItems() {
        List<Item> items = service.getAllItems();
        if (items.isEmpty()) {
            System.out.println("(no items)");
            return;
        }
        Optional<SortBy> sortBy = readSortBy();
        if (sortBy.isEmpty()) {
            return;
        }
        listItems(ItemSorter.sort(items, sortBy.get()));
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
    private Optional<SortBy> readSortBy() {
        System.out.println("Sort by:");
        System.out.println("  1. Name (A-Z)");
        System.out.println("  2. Name (Z-A)");
        System.out.println("  3. Category");
        while (true) {
            System.out.print("Choice (Enter = A-Z, 'b' = back): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("b")) {
                return Optional.empty();
            }
            switch (input) {
                case "", "1":
                    return Optional.of(SortBy.NAME_ASC);
                case "2":
                    return Optional.of(SortBy.NAME_DESC);
                case "3":
                    return Optional.of(SortBy.CATEGORY);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---- input helpers: return empty when the user types 'b' to go back ----
    private Optional<String> readString(String prompt) {
        System.out.print(prompt + " (or 'b' to go back): ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("b")) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    private Optional<Integer> readInt(String prompt) {
        while (true) {
            System.out.print(prompt + " (or 'b' to go back): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("b")) {
                return Optional.empty();
            }
            try {
                return Optional.of(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private Optional<Double> readDouble(String prompt) {
        while (true) {
            System.out.print(prompt + " (or 'b' to go back): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("b")) {
                return Optional.empty();
            }
            try {
                return Optional.of(Double.parseDouble(input));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (e.g. 9.99).");
            }
        }
    }

    private Optional<Category> readCategory(String prompt) {
        Category[] values = Category.values();
        System.out.println("Categories:");
        for (int i = 0; i < values.length; i++) {
            System.out.println("  " + (i + 1) + ". " + values[i]);
        }
        while (true) {
            System.out.print(prompt + " (or 'b' to go back): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("b")) {
                return Optional.empty();
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= values.length) {
                    return Optional.of(values[choice - 1]);
                }
                System.out.println("Out of range.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
