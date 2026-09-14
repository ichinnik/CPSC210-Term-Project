package persistence;

import model.Item;
import model.Character;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFileHere.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }     
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            Character c = reader.read();
            assertEquals("John", c.getCharacterName());
            assertEquals("Knight", c.getCharacterClass());
            assertEquals(3, c.getLevel());
            assertEquals("Silly goose", c.getBackground());
            assertTrue(c.getInventory().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCharacter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCharacter.json");
        try {
            Character c = reader.read();
            assertEquals("John", c.getCharacterName());
            assertEquals("Knight", c.getCharacterClass());
            assertEquals(3, c.getLevel());
            assertEquals("Silly goose", c.getBackground());
            ArrayList<Item> inventory = c.getInventory();
            assertEquals(2, inventory.size());
            checkItem("Sword", "weapon", 10, 25, true, true, inventory.get(0));
            checkItem("Potion", "consumable", 5, 67, false, false, inventory.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
