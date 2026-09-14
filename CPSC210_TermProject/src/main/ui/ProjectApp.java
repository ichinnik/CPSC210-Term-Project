package ui;

import model.Character;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

// SOURCE***: Code inspired by TellerApp, in order to implement UI
// INSPIRATION: Many fields taken from D&D 5E Character Sheet, as well as Weapons attributes (ex. weight, value)

// D&D Character Sheet Application
public class ProjectApp {
    private static final String JSON_STORE = "./data/character.json";
    private Character character;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the project application
    public ProjectApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runProject();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runProject() {
        input = new Scanner(System.in);
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Farewell, good luck on your travels!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("x")) {
            doExamine();
        } else if (command.equals("a")) {
            doAddItem();
        } else if (command.equals("d")) {
            doDropItem();
        } else if (command.equals("v")) {
            doViewInventory();
        } else if (command.equals("f")) {
            doFavoriteItem();
        } else if (command.equals("n")) {
            doUnfavoriteItem();
        } else if (command.equals("e")) {
            doEquipItem();
        } else if (command.equals("u")) {
            doUnequipItem();
        } else if (command.equals("s")) {
            saveCharacter();
        } else if (command.equals("l")) {
            loadCharacter();
        } else {
            System.out.println("Oops! Try again.");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: initializes a placeholder character, and asks if user wishes to create or load a character
    // character
    private void init() {
        Boolean choosing = true;
        character = new Character("Placeholder", "Nothing", "Initialized by a method");
        String command = null; 
        System.out.println(
                "First, would you like to create a character or automatically load an existing one? Type c or l.");
        command = input.next();
        input.nextLine();
        while (choosing) {
            if (command.equals("c")) {
                choosing = false;
                createCharacter();
            } else if (command.equals("l")) {
                choosing = false;
                loadCharacter();
            } else {
                System.out.println("Rats! Try again.");
                choosing = false;
                init();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a character according to
    // parameters provided by the user
    private void createCharacter() {
        boolean creation = true;
        String myName = "null";

        while (creation) {
            askName(myName);
            creation = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: helper methods for createCharacter(),
    // proceeding four code blocks
    private void askName(String myName) {
        String myClass = "empty";
        System.out.println("\nFirst off, what is thy name?");
        myName = input.nextLine();
        if (myName != "null") {
            character.setCharacterName(myName);
            askClass(myClass);
        }
    }

    private void askClass(String myClass) {
        int myLevel = 0;
        System.out.println("\nExcellent! Now, what is thy class?");
        myClass = input.nextLine();
        if (myClass != "empty") {
            character.setCharacterClass(myClass);
            askLevel(myLevel);
        }
    }

    private void askLevel(int myLevel) {
        String myBackground = "zilch";
        System.out.println("\nMarvelous! And what doth be thy level?");
        System.out.println("Please enter a number, or I shall cast a Magic Missile at your program.");
        myLevel = input.nextInt();
        if (myLevel != 0) {
            character.setLevel(myLevel);
            askBackground(myBackground);
        }

    }

    private void askBackground(String myBackground) {
        System.out.println("\nFantastic! Finally, what beeth thou background?");
        input.nextLine();
        myBackground = input.nextLine();
        if (myBackground != "zilch") {
            System.out.println("\nJust wonderful! Thank you for your cooperation.");
            character.setBackground(myBackground);
        }
    }

    // EFFECTS: displays directory of functions for user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do, adventurer?");
        System.out.println("~--~--~--~--~--~--~--~--~--~--~--~--~--~");
        System.out.println("\n\tx ~> Examine Yourself");
        System.out.println("\ta ~> Add an Item to your Inventory");
        System.out.println("\td ~> Drop an Item from your Inventory");
        System.out.println("\tv ~> View your Inventory");
        System.out.println("\tf ~> Favorite an Item");
        System.out.println("\tn ~> Unfavorite an Item");
        System.out.println("\te ~> Equip an Item");
        System.out.println("\tu ~> Unequip an Item");
        System.out.println("\ts ~> Save Character to File");
        System.out.println("\tl ~> Load Character from File");
        System.out.println("\tq ~> Quit the Application");
    }

    // EFFECTS: prints all character information excluding inventory
    private void doExamine() {
        System.out.println("\n" + character.getCharacterName() + ", you are a level " + character.getLevel() + " "
                + character.getCharacterClass() + "!");
        System.out.println("\nYour background: " + character.getBackground());
        System.out.println("\nSuch a brave hero!\n");
    }

    // REQUIRES: entries to follow example
    // MODIFIES: this
    // EFFECTS: creates a new Item, and adds
    // it to character's inventory
    private void doAddItem() {
        Item newItem = new Item("empty", "none", 1, 2);
        System.out.println("\nWhat is the name (one word), type (lowercase), weight, and value of your item?");
        System.out.println("\n(ex. Scroll consumable 10 25)");
        newItem.setItemName(input.next());
        newItem.setType(input.next());
        newItem.setWeight(input.nextInt());
        newItem.setValue(input.nextInt());

        System.out.println("\nAdded to inventory!");
        character.addItem(newItem);
    }

    // MODIFIES: this
    // EFFECTS: conducts the dropping of an item
    private void doDropItem() {
        boolean found = false;
        System.out.println("\nWhat is the name of the item that you wish to drop?");
        String dropName = input.next();

        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(dropName)) {
                character.getInventory().remove(i);
                System.out.println("\nItem successfully removed!");
                found = true;
                break;
            }
        }

        if (found != true) {
            System.out.println("\nHmm... looks like something went wrong...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the viewing of the inventory
    private void doViewInventory() {
        if (character.getInventory().isEmpty()) {
            System.out.println("\nGet some items first, you plebeian!");
            return;
        }
        System.out.println("\n");
        for (Item item : character.getInventory()) {
            System.out.println("\t" + item.getItemName() + " -- " + "Type: " + item.getType() + ", Weight: "
                    + Integer.toString(item.getWeight()) + " lb" + ", Value: " + Integer.toString(item.getValue())
                    + " gold");
            if (item.isFavorite()) {
                System.out.println("\n *** Favorite! ***\n");
            }
            if (item.isEquipped()) {
                System.out.println("\n *** Equipped! ***\n");
            }
        }
        System.out.println("\n");
    }

    // MODIFIES: this
    // EFFECTS: conducts the favoriting of an item
    private void doFavoriteItem() {
        boolean found = false;
        System.out.println("\nWhat is the name of the item that you wish to favorite?");
        String favoriteName = input.next();

        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(favoriteName)) {
                if (character.getInventory().get(i).isFavorite()) {
                    System.out.println("\nAlready favorited!");
                    return;
                }
                character.getInventory().get(i).favorite();
                System.out.println("\nItem successfully favorited!");
                found = true;
                return;
            }
        }

        if (found != true) {
            System.out.println("\nCouldn't favorite that item... maybe give it another go?");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the unfavoriting of an item
    private void doUnfavoriteItem() {
        boolean found = false;
        System.out.println("\nWhat is the name of the item that you wish to unfavorite?");
        String unfavoriteName = input.next();

        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(unfavoriteName)) {
                if (!character.getInventory().get(i).isFavorite()) {
                    System.out.println("\nAlready unfavorited!");
                    return;
                }
                character.getInventory().get(i).unfavorite();
                System.out.println("\nItem successfully unfavorited!");
                found = true;
                return;
            }
        }

        if (found != true) {
            System.out.println("\nCouldn't unfavorite that item... perchance one more attempt?");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the equipping of an item
    private void doEquipItem() {
        boolean found = false;
        System.out.println("\nWhat is the name of the item that you wish to equip?");
        String equipName = input.next();

        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(equipName)) {
                if (character.getInventory().get(i).isEquipped()) {
                    System.out.println("\nAlready equipped!");
                    return;
                }
                character.getInventory().get(i).equip();
                System.out.println("\nItem successfully equipped!");
                found = true;
                return;
            }
        }

        if (found != true) {
            System.out.println("\nCouldn't equip that item... try a little harder?");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the unequipping of an item
    private void doUnequipItem() {
        boolean found = false;
        System.out.println("\nWhat is the name of the item that you wish to equip?");
        String equipName = input.next();

        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i).getItemName().equals(equipName)) {
                if (!character.getInventory().get(i).isEquipped()) {
                    System.out.println("\nAlready unequipped!");
                    return;
                }
                character.getInventory().get(i).unequip();
                System.out.println("\nItem successfully unequipped!");
                found = true;
                return;
            }
        }

        if (found != true) {
            System.out.println("\nCouldn't unequip that item... maybe try typing with more gusto?");
        }
    }

    // EFFECTS: saves the character to file
    private void saveCharacter() {
        try {
            jsonWriter.open();
            jsonWriter.write(character);
            jsonWriter.close();
            System.out.println("Saved " + character.getCharacterName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads the character from file
    private void loadCharacter() {
        try {
            character = jsonReader.read();
            System.out.println("Loaded " + character.getCharacterName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
