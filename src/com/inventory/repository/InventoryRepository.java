package com.inventory.repository;

import com.inventory.model.Category;
import com.inventory.model.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The ONLY class that knows how items are stored.
 * Right now it uses an in-memory map and can save/load that map as a text file.
 */
public class InventoryRepository {
    private final Map<Integer, Item> items = new LinkedHashMap<>();
    private int nextId = 1;

    public int generateId() {
        return nextId++;
    }

    public void save(Item item) {
        items.put(item.getId(), item);
    }

    public Item findById(int id) {
        return items.get(id); // returns null if not found
    }

    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    public boolean deleteById(int id) {
        return items.remove(id) != null;
    }

    /** Writes one item per line: id|name|quantity|price|category */
    public void saveToFile(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Item item : items.values()) {
            lines.add(item.getId() + "|" + item.getName() + "|" + item.getQuantity()
                    + "|" + item.getPrice() + "|" + item.getCategory());
        }
        Files.write(path, lines);
    }

    /**
     * Replaces the current items with the contents of the file.
     * If the file does not exist, nothing changes.
     */
    public void loadFromFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }

        // Parse into a temporary map first, so a bad file doesn't wipe current data.
        Map<Integer, Item> loaded = new LinkedHashMap<>();
        int maxId = 0;
        List<String> lines = Files.readAllLines(path);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|", -1);
            if (parts.length != 5) {
                throw new IOException("Wrong format on line " + (i + 1));
            }
            try {
                int id = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                Category category = Category.valueOf(parts[4]);
                loaded.put(id, new Item(id, parts[1], quantity, price, category));
                maxId = Math.max(maxId, id);
            } catch (IllegalArgumentException e) {
                // NumberFormatException and Category.valueOf's error are both subclasses of this
                throw new IOException("Bad value on line " + (i + 1) + ": " + e.getMessage());
            }
        }

        items.clear();
        items.putAll(loaded);
        nextId = maxId + 1; // so new items don't reuse loaded ids
    }
}
