package com.inventory;

import com.inventory.repository.InventoryRepository;
import com.inventory.service.InventoryService;
import com.inventory.ui.ConsoleMenu;

/**
 * Entry point only. It wires the layers together and starts the UI.
 * No business logic belongs here.
 */
public class Main {
    public static void main(String[] args) {
        InventoryRepository repository = new InventoryRepository();
        InventoryService service = new InventoryService(repository);
        ConsoleMenu menu = new ConsoleMenu(service);
        menu.run();
    }
}
