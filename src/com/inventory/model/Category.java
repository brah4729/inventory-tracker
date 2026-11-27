package com.inventory.model;

/**
 * Fixed set of categories. An enum is simpler than a full class for this.
 */
public class Category {

    private final int id;
    private String name;
    private String description;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }


    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
   
}
