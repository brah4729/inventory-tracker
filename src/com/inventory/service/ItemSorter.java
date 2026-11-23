package com.inventory.service;

import com.inventory.model.Item;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ItemSorter {

    public enum SortBy {
        NAME_ASC, NAME_DESC, CATEGORY_ASC, CATEGORY_DESC, QUANT_ASC, QUANT_DESC
    }

    private ItemSorter() {

    }

    public static List<Item> sort(List<Item> items, SortBy sortBy) {
        List<Item> sorted = new ArrayList<>(items);
        Comparator<Item> byName = Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        switch (sortBy) {
            case NAME_ASC ->
                sorted.sort(byName);
            case NAME_DESC ->
                sorted.sort(byName.reversed());
            case CATEGORY_ASC ->
                sorted.sort(Comparator.comparing((Item i ) -> i.getCategory().name()).thenComparing(byName));
            case CATEGORY_DESC ->
                sorted.sort(Comparator.comparing((Item i ) -> i.getCategory().name()).reversed().thenComparing(byName));
            case QUANT_ASC ->
                sorted.sort(Comparator.comparingInt(Item::getQuantity).thenComparing(byName));
            case QUANT_DESC ->
                sorted.sort(Comparator.comparingInt(Item::getQuantity).reversed().thenComparing(byName));
        }
        return sorted;
    }
}
