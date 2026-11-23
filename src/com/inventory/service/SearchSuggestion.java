package com.inventory.service;

import com.inventory.exception.OutOfStockException;
import com.inventory.model.Category;
import com.inventory.model.Item;
import com.inventory.repository.InventoryRepository;
import java.util.*;

public class SearchSuggestion {

    private final InventoryRepository repository;

    public SearchSuggestion(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Item> suggestItems(List<Item> items, String input, int limit) {
        String normalizedQuery = input.trim().toLowerCase();
        List<String> startsWith = new ArrayList<>();
        List<String> contains = new ArrayList<>();
        for (Item item : items) {
            String name = item.getName().tolowercase();
            if (name.startsWith(normalizedQuery)) {
                startsWith.add(item.getName()); 
            }else if (name.contains(normalizedQuery)) {
                contains.add(item.getName());
            }
        }
        startsWith.addAll(contains);
        return startsWith.subList(0, Math.min(limit, startsWith.size()));
    }
}
