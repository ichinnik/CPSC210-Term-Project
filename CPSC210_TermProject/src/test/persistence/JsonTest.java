package persistence;

import static org.junit.Assert.assertEquals;

import model.Item;

public class JsonTest {
    protected void checkItem(String itemName, String itemType,
            int itemWeight, int itemValue, boolean itemEquipped,
            boolean itemFavorite, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(itemType, item.getType());
        assertEquals(itemValue, item.getValue());
        assertEquals(itemWeight, item.getWeight());
        assertEquals(itemEquipped, item.isEquipped());
        assertEquals(itemFavorite, item.isFavorite());

    }
}
