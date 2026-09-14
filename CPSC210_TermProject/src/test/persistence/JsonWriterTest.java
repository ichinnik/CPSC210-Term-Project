package persistence;

import model.Character;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0notokay:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Character c = new Character("Ulfric", "Warrior", "High King");
            c.setLevel(30);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            c = reader.read();
            assertEquals("Ulfric", c.getCharacterName());
            assertEquals("Warrior", c.getCharacterClass());
            assertEquals("High King", c.getBackground());
            assertEquals(30, c.getLevel());
            assertTrue(c.getInventory().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCharacter() {
        try {
            Character c = new Character("Ulfric", "Warrior", "High King");
            c.setLevel(30);
            c.addItem(new Item("Potion", "consumable", 5, 67));
            c.getInventory().get(0).favorite();
            c.getInventory().get(0).equip();
            c.addItem(new Item("Sword", "weapon", 10, 25));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCharacter.json");
            writer.open();
            writer.write(c);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralCharacter.json");
            c = reader.read();
            assertEquals("Ulfric", c.getCharacterName());
            assertEquals("Warrior", c.getCharacterClass());
            assertEquals("High King", c.getBackground());
            assertEquals(30, c.getLevel());
            ArrayList<Item> inventory = c.getInventory();
            checkItem("Potion", "consumable", 5, 67, true, true, inventory.get(0));
            checkItem("Sword", "weapon", 10, 25, false, false, inventory.get(1));
        
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
