package com.inventory.service;

import com.inventory.model.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ItemSorter {

    public enum SortBy {
        ASCENDING, DESCENDING, CATEGORY
    }

    private ItemSorter() {

    }

    public static List<Item> sort(List<Item> items, SortBy sortBy) {
        List<Item> sorted = new ArrayList<>(items);
        Comparator<Item> byName = Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        switch (sortBy) {
            case ASCENDING ->
                sorted.sort(byName);
            case DESCENDING ->
                sorted.sort(byName.reversed());
            case CATEGORY ->
                sorted.sort(Comparator.comparing(Item::getCategory).thenComparing(byName));
        }
        return sorted;
    }
}
