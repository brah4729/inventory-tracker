package com.inventory.model;

/** Plain data class: represents one product in the inventory. */
public class Item {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private Category category;

    public Item(int id, String name, int quantity, double price, Category category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return String.format("#%d | %-15s | qty: %-4d | $%.2f | %s",
                id, name, quantity, price, category);
    }
}
