import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ItemDefinition {
    private String name;
    private String description;
    private String[] componentNames;
    private boolean isBaseItem;
    private Optional<Double> weight;
    private ItemDictionary dict; 

    public ItemDefinition(String n, String desc, Optional<Double> weightIfBase, String[] components){
        name = n;
        description = desc;
        componentNames = components;
        isBaseItem = weightIfBase.isPresent();
        weight = weightIfBase;
        dict = ItemDictionary.get();

    }

    /**
     * Creating an instance of the item described by this ItemDefinition.
     * If the Item is made up of other items, then each sub-item should also be created.
     * @return An Item instance described by the ItemDefinition
     */
    public ItemInterface create() {
        // If it's a base item, create and return a BaseItem
        if (isBaseItem) {
            return new Item(this);
        }
    
        // If it's a composite item, create the composite item and its components
        List<ItemInterface> componentsList = new ArrayList<>();
        for (String componentName : componentNames) {
            Optional<ItemDefinition> componentDef = dict.defByName(componentName);
            if (componentDef.isPresent()) {
                componentsList.add(componentDef.get().create());  // Recursively create the component
            }
        }
    
        return new CompositeItem(this, componentsList);
    }
    

  
    public boolean craft(Player player) throws ItemNotCraftableException  {
        if (isBaseItem) {
            throw new ItemNotCraftableException(this); // base items cannot be crafted.
        }
        
        CompositeItem craftedItem = new CompositeItem(this, new ArrayList<>());
        List<ItemInterface> itemsUsedForCrafting = new ArrayList<>(); // To keep track of items used for crafting
    
        // Convert the array to a list
        List<String> componentsToFind = new ArrayList<>(Arrays.asList(componentNames));
    
        // Loop through all items in player's inventory
        for (ItemInterface inventoryItem : player.getInventory().searchItems("")) {
            ItemDefinition inventoryItemDefinition = dict.defByName(inventoryItem.getDefinition().getName()).orElse(null);
            if (inventoryItemDefinition == null) {
                System.out.println("Warning: Definition for " + inventoryItem.getDefinition().getName() + " not found in ItemDictionary.");
                continue;  // Skip items that are not in the dictionary
            }
            if (inventoryItem.getDefinition().isBaseItem && componentsToFind.contains(inventoryItem.getDefinition().getName())) {
                craftedItem.addComponent(inventoryItem);
                itemsUsedForCrafting.add(inventoryItem); 
                componentsToFind.remove(inventoryItem.getDefinition().getName());
    
                try {
                    player.getInventory().remove(inventoryItem);
                } catch (ItemNotAvailableException e) {
                   
                    // Return items used for crafting back to inventory
                    for (ItemInterface item : itemsUsedForCrafting) {
                        player.getInventory().addOne(item);
                    }
                    throw new ItemNotCraftableException(this);
  
                }
            }
        }
    
        // If not all components are found, return items back and return false
        if (!componentsToFind.isEmpty()) {
            for (ItemInterface item : itemsUsedForCrafting) {
                player.getInventory().addOne(item);
            }
            throw new ItemNotCraftableException(this);
        }
        
        player.getInventory().addOne(craftedItem);
        return true;
    }
    
   
    public boolean uncraft(Player player) {
        if (isBaseItem) {
            return false; // base items cannot be uncrafted.
        }
    
        // Attempt to remove the composite item from the player's inventory if it's there.
        ArrayList<ItemInterface> foundItems = player.getInventory().searchItems(name);
        if (!foundItems.isEmpty()) {
            try {
                player.getInventory().removeOne(this);
            } catch (ItemNotAvailableException e) {
                // e.printStackTrace();
                return false;
            }
        }
    
        // For each component in the composite item, check if it's composite or base and handle accordingly.
        for (String componentName : componentNames) {
            ItemDefinition componentDef = dict.defByName(componentName).orElse(null);
            if (componentDef == null) {
                System.out.println("Warning: Definition for " + componentName + " not found in ItemDictionary.");
                continue;
            }
    
            if (componentDef.isBaseItemDef()) {
                // Directly add base items to the player's inventory
                player.getInventory().addOne(componentDef.create());
            } else {
                // If it's a composite item, recursively uncraft it
                componentDef.uncraft(player);
            }
        }
    
        return true;
    }

    // ItemDefinition might "craft" and return an item, using items from some source inventory.
    

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Format: {ITEM 1}, {ITEM 2}, ...
     * @return a String of sub-item/component names in the above format
     */
    public String componentsString() {
        String output = "";
        for (String componentName : componentNames) {
            output += componentName + ", ";
        }
        return output;
    }

    public boolean isBaseItemDef() {
        return isBaseItem;
    }

    public Optional<Double> getWeight() {
        return weight;
    }

    public boolean equals(Item other) {
        return isOf(other.getDefinition());
    }

	public boolean isOf(ItemDefinition def) {
		return getName().equals(def.getName());
	}

}
