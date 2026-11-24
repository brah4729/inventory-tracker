package com.inventory.service;

import com.inventory.exception.OutOfStockException;
import com.inventory.model.Category;
import com.inventory.model.Item;
import com.inventory.repository.InventoryRepository;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/**
 * Business rules live here (validation, stock checks, searching, sorting).
 */
public class InventoryService {

    private final InventoryRepository repository;
    private final SearchSuggestion suggestion = new SearchSuggestion();

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public Item addItem(String name, int quantity, double price, Category category) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        Item item = new Item(repository.generateId(), name.trim(), quantity, price, category);
        repository.save(item);
        return item;
    }

    public void addStock(int id, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        Item item = getItemOrThrow(id);
        item.setQuantity(item.getQuantity() + amount);
    }

    public void removeStock(int id, int amount) throws OutOfStockException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        Item item = getItemOrThrow(id);
        if (amount > item.getQuantity()) {
            throw new OutOfStockException("Only " + item.getQuantity()
                    + " of '" + item.getName() + "' in stock.");
        }
        item.setQuantity(item.getQuantity() - amount);
    }

    public void deleteItem(int id) {
        if (!repository.deleteById(id)) {
            throw new IllegalArgumentException("No item with id " + id);
        }
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public List<Item> getSortedItems(ItemSorter.SortBy sortBy) {
        return ItemSorter.sort(repository.findAll(), sortBy);
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be empty.");
        }
        return suggestion.suggestItems(repository.findAll(), keyword, Integer.MAX_VALUE);
    }

    public List<Item> getLowStockItems(int threshold) {
        List<Item> results = new ArrayList<>();
        for (Item item : repository.findAll()) {
            if (item.getQuantity() <= threshold) {
                results.add(item);
            }
        }
        return results;
    }

    public void save(Path path) throws IOException {
        repository.saveToFile(path);
    }

    public void load(Path path) throws IOException {
        repository.loadFromFile(path);
    }

    private Item getItemOrThrow(int id) {
        Item item = repository.findById(id);
        if (item == null) {
            throw new IllegalArgumentException("No item with id " + id);
        }
        return item;
    }
}
