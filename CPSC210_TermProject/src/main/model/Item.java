package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item having a name, type, weight (in lbs), value (in gold pieces),
// and whether it is equipped/favorite or not
public class Item implements Writable {
    private String name;
    private String type; // e.g. "weapon", "armor", "consumable", "other"
    private int weight;
    private int value;
    private Boolean equipped;
    private Boolean favorite;

    /*
     * EFFECTS: creates an item with a name, weight,
     * value, and equipped/favorite states (start false)
     */
    public Item(String itemName, String itemType, int itemWeight, int itemValue) {
        this.name = itemName;
        this.type = itemType;
        this.weight = itemWeight;
        this.value = itemValue;
        this.equipped = false;
        this.favorite = false;
    }

    public void setItemName(String newName) {
        this.name = newName;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    /*
     * REQUIRES: equipped == false
     * MODIFIES: this
     * EFFECTS: equips an item
     */
    public void equip() {
        this.equipped = true;
    }

    /*
     * REQUIRES: equipped == true
     * MODIFIES: this
     * EFFECTS: unequips an item
     */
    public void unequip() {
        this.equipped = false;
    }

    /*
     * REQUIRES: favorite == false
     * MODIFIES: this
     * EFFECTS: makes an item favorite
     */
    public void favorite() {
        this.favorite = true;
    }

    /*
     * REQUIRES: favorite == true
     * MODIFIES: this
     * EFFECTS: removes favorite from an item
     */
    public void unfavorite() {
        this.favorite = false;
    }

    public String getItemName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public Boolean isEquipped() {
        return equipped;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    // EFFECTS: converts the item to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("itemType", type);
        json.put("weight", weight);
        json.put("value", value);
        json.put("equipped", equipped);
        json.put("favorite", favorite);
        return json;
    }
}
