package seedu.address.inventory.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ItemBuilder;

public class ItemTest {
    @Test
    public void isSameItemTest() {
        Item item = new ItemBuilder().withDescription("Test Item").withCategory("test").withQuantity(5)
                .withCost(3.0).withPrice(10.0).withId("2").build();
        Item itemCopy = new ItemBuilder().withDescription("Test Item").withCategory("test").withQuantity(5)
                .withCost(3.0).withPrice(10.0).withId("2").build();
        Item item2 = item;
        item2.setPrice(2.0);
        Item item3 = item;
        item3.setId(4);
        Item item4 = new ItemBuilder().withDescription("Different Test Item").withCategory("test").withQuantity(5)
                .withCost(3.0).withPrice(10.0).withId("2").build();

        //It is considered the same item if description and category
        assertTrue(item.isSameItem(itemCopy));
        //It is considered the same item even if the price and ID are different
        assertTrue(item.isSameItem(item2));
        assertTrue(item.isSameItem(item3));
        assertFalse(item.isSameItem(item4));
    }
}
