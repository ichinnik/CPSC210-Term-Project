package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestItem {
    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item("Stick", "weapon", 2, 1);
    }

    @Test
    void itemConstructorTest() {
        assertEquals("Stick", testItem.getItemName());
        testItem.setItemName("Cuirass");
        assertEquals("Cuirass", testItem.getItemName());
        assertEquals("weapon", testItem.getType());
        testItem.setType("armor");
        assertEquals("armor", testItem.getType());
        assertEquals(2, testItem.getWeight());
        testItem.setWeight(6);
        assertEquals(6, testItem.getWeight());
        assertEquals(1, testItem.getValue());
        testItem.setValue(42);
        assertEquals(42, testItem.getValue());
        assertFalse(testItem.isEquipped());
        assertFalse(testItem.isFavorite());
    }

    @Test
    void itemEquipTest() {
        assertFalse(testItem.isEquipped());
        testItem.equip();
        assertTrue(testItem.isEquipped());
    }

    @Test
    void itemUnquipTest() {
        testItem.equip();
        assertTrue(testItem.isEquipped());
        testItem.unequip();
        assertFalse(testItem.isEquipped());
    }

    @Test
    void itemFavoriteTest() {
        assertFalse(testItem.isFavorite());
        testItem.favorite();
        assertTrue(testItem.isFavorite());
    }

    @Test
    void itemUnfavoriteTest() {
        testItem.favorite();
        assertTrue(testItem.isFavorite());
        testItem.unfavorite();
        assertFalse(testItem.isFavorite());
    }
}
