package com.inventory.service;

import com.inventory.model.Item;
import java.util.*;

public class SearchSuggestion {


    public List<Item> suggestItems(List<Item> items, String input, int limit) {
        if (input == null || input .isBlank() || limit <= 0 ){
            return Collections.emptyList();
        }
        String normalizedQuery = input.trim().toLowerCase();
        List<Item> startsWith = new ArrayList<>();
        List<Item> contains = new ArrayList<>();

        for (Item item : items) {
            String name = item.getName().toLowerCase();
            if (name.startsWith(normalizedQuery)) {
                startsWith.add(item); 
            }else if (name.contains(normalizedQuery)) {
                contains.add(item);
            }
        }
        startsWith.addAll(contains);
        return startsWith.subList(0, Math.min(limit, startsWith.size()));
    }
}
