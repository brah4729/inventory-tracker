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

    public List<Item> suggestItems(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        String normalizedQuery = query.trim().toLowerCase();
        return
    }
}