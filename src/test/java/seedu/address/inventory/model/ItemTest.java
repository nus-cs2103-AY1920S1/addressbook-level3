package seedu.address.inventory.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalItem;

public class ItemTest {
    @Test
    public void isSameItemTest() {
        Item fishBurger1 = TypicalItem.FISH_BURGER;
        Item fishBurger1Copy = TypicalItem.FISH_BURGER;
        Item fishBurger2 = TypicalItem.FISH_BURGER;
        fishBurger2.setPrice(2.0);
        Item fishBurger3 = TypicalItem.FISH_BURGER;
        fishBurger3.setId(4);
        Item notFishBurger = TypicalItem.BURGER_AND_CHIPS;

        assertTrue(fishBurger1.isSameItem(fishBurger1Copy));
        //It is considered the same item even if the price and ID are different
        assertTrue(fishBurger1.isSameItem(fishBurger2));
        assertTrue(fishBurger1.isSameItem(fishBurger3));
        assertFalse(fishBurger1.isSameItem(notFishBurger));
    }
}
