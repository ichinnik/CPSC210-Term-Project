package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.ArrayList;

// Represents the player character having a name, inventory (list of items), 
// class, level, and background
public class Character implements Writable {
    private String name; // Character name (starts as "Just A. Adventurer")
    private ArrayList<Item> inventory; // Character inventory, starts empty
    private String characterClass; // Character class
    private int level; // Character level (starts at 1)
    private String background; // Character background

    /*
     * EFFECTS: creates a character
     */
    public Character(String newName, String newClass, String newBackground) {
        this.name = newName;
        this.inventory = new ArrayList<Item>();
        this.characterClass = newClass;
        this.level = 1;
        this.background = newBackground;
    }

    /*
     * EFFECTS: this
     * MODIFIES: adds an item to the inventory
     */
    public void addItem(Item newItem) {
        inventory.add(newItem);
    }

    /*
     * REQUIRES: item to already be in inventory
     * EFFECTS: this
     * MODIFIES: drops an item from the inventory by
     * inputting the desired item's name
     */
    public void dropItem(String droppedName, Item droppedItem) {
        if (droppedItem.getItemName() == droppedName) {
            inventory.remove(droppedItem);
        }
    }

    public void setCharacterName(String n) {
        this.name = n;
    }

    public void setCharacterClass(String c) {
        this.characterClass = c;
    }

    public void setLevel(int l) {
        this.level = l;
    }

    public void setBackground(String b) {
        this.background = b;
    }

    public String getCharacterName() {
        return name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public int getLevel() {
        return level;
    }

    public String getBackground() {
        return background;
    }

    // EFFECTS: converts the item to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("inventory", inventoryToJson());
        json.put("characterClass", characterClass);
        json.put("level", level);
        json.put("background", background);
        return json;
    }

    // EFFECTS: returns items in character's inventory as a JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : inventory) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
