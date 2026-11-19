
public class InventoryTracker {
    private Map<String, Integer> inventory = new HashMap<>();
    

    //search for a specific item in the inventory
    public static void main(String[] args) {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(itemName)) {
                return entry.getValue();
            }
        }
    }

    //add item and quantity to an existing item in the inventory


    //remove item from the inventory
    
    //categories 

    //store all of that in a local file storage 
}
