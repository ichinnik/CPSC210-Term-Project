package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacter {
    private Character testCharacter;
    private Item testItem;
    private Item testItem2;

    @BeforeEach
    void runBefore() {
        testCharacter = new Character("Johnny Test", "Ranger", "Raised by wolves");
        testItem = new Item("Iron Helmet", "armor", 10, 15);
        testItem2 = new Item("Cool Staff", "weapon", 5, 20);
    }

    @Test
    void characterConstructorTest() {
        assertEquals("Johnny Test", testCharacter.getCharacterName());
        testCharacter.setCharacterName("Big Orc");
        assertEquals("Big Orc", testCharacter.getCharacterName());
        assertTrue(testCharacter.getInventory().isEmpty());
        assertEquals("Ranger", testCharacter.getCharacterClass());
        testCharacter.setCharacterClass("Barbarian");
        assertEquals("Barbarian", testCharacter.getCharacterClass());
        assertEquals(1, testCharacter.getLevel());
        testCharacter.setLevel(9000);
        assertEquals(9000, testCharacter.getLevel());
        testCharacter.setBackground("Ruthless bandit");
        assertEquals("Ruthless bandit", testCharacter.getBackground());
    }

    @Test
    void characterAddItemTest() {
        testCharacter.addItem(testItem);
        assertFalse(testCharacter.getInventory() == null);
        testCharacter.addItem(testItem2);
        assertTrue(testCharacter.getInventory().get(1) == testItem2);
    }

    @Test
    void characterDropItemTest() {
        testCharacter.addItem(testItem);
        assertFalse(testCharacter.getInventory().get(0) == null);
        testCharacter.dropItem("Iron Helmet", testItem);
        assertTrue(testCharacter.getInventory().isEmpty());
        testCharacter.dropItem("Monkey Wrench", testItem);
        assertTrue(testCharacter.getInventory().isEmpty());
    }
}
