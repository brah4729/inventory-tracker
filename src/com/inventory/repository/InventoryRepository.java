package com.inventory.repository;

import com.inventory.model.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The ONLY class that knows how items are stored.
 * Right now it uses an in-memory map. Later you can replace the internals
 * with file or database code without changing the other classes.
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
}
