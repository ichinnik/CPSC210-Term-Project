package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterManager {
    private static final String JSON_STORE = "./data/character.json";
    private Character character;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates a new CharacterManager(),
    // specifies where to store data
    public CharacterManager() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public void createCharacter(String name, String charClass,
            String background, String level) throws NumberFormatException {
        character = new Character(name, charClass, background);
        character.setLevel(Integer.parseInt(level));
        EventLog.getInstance().logEvent(new Event("Character created."));
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the characters inventory
    public void addItem(Item item) {
        if (character != null) {
            character.addItem(item);
            EventLog.getInstance().logEvent(new Event("Item added to character's inventory."));
        }
    }

    // MODIFIES: this
    // EFFECTS: drops an item from the characters inventory
    public Boolean dropItem(Character character, String dropName) {
        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(dropName)) {
                character.getInventory().remove(i);
                EventLog.getInstance().logEvent(new Event("Item dropped from character's inventory."));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Item to be dropped not found."));
        return false;
    }

    // MODIFIES: this
    // EFFECTS: (un)favorites an item by searching through the
    // character's inventory, returns true if found
    public Boolean favoriteItem(Character character, String itemName) {
        for (int i = 0; i < character.getInventory().size(); i++) {
            Item item = character.getInventory().get(i);

            if (item.getItemName().equals(itemName)) {
                if (item.isFavorite()) {
                    item.unfavorite();
                    EventLog.getInstance().logEvent(new Event("Unfavorited an item in the character's inventory."));
                    return true;
                }
                item.favorite();
                EventLog.getInstance().logEvent(new Event("Favorited an item in the character's inventory."));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Could not find an item to equip."));
        return false;
    }

    // MODIFIES: this
    // EFFECTS: (un)equips an item by searching through the
    // character's inventory, returns true if found
    public Boolean equipItem(Character character, String itemName) {
        for (int i = 0; i < character.getInventory().size(); i++) {
            Item item = character.getInventory().get(i);

            if (item.getItemName().equals(itemName)) {
                if (item.isEquipped()) {
                    item.unequip();
                    EventLog.getInstance().logEvent(new Event("Unequipped an item in the character's inventory."));
                    return true;
                }
                item.equip();
                EventLog.getInstance().logEvent(new Event("Equipped an item in the character's inventory."));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Could not find an item to equip."));
        return false;
    }

    public ArrayList<Item> getInventory() {
        if (character.getInventory() == null) {
            EventLog.getInstance().logEvent(new Event("Retrieved a character's empty inventory"));
            return null;
        }
        EventLog.getInstance().logEvent(new Event("Retrieved character's inventory."));
        return character.getInventory();
    }

    // MODIFIES: this, character.json
    // EFFECTS: saves a character and their inventory to file
    public void saveCharacter() throws IOException {
        if (character == null) {
            Character empty = new Character("", "", "");
            jsonWriter.open();
            jsonWriter.write(empty);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Saved an empty character to file."));
        }
        if (character != null) {
            jsonWriter.open();
            jsonWriter.write(character);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Saved a character to file."));
        }
    }

    // EFFECTS: loads a character and their inventory from file
    public void loadCharacter() throws IOException {
        character = jsonReader.read();
        EventLog.getInstance().logEvent(new Event("Loaded a character from file."));
    }

    public Character getCharacter() {
        return character;
    }
}
