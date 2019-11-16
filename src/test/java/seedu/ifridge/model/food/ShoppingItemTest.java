package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.TypicalBoughtList.EGG;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalShoppingList.APPLE;
import static seedu.ifridge.testutil.TypicalShoppingList.BANANA;
import static seedu.ifridge.testutil.TypicalShoppingList.BOUGHT_EGGS;
import static seedu.ifridge.testutil.TypicalShoppingList.CAKE;
import static seedu.ifridge.testutil.TypicalShoppingList.EGGS;
import static seedu.ifridge.testutil.TypicalShoppingList.URGENT_EGGS;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ShoppingItemTest {

    /**
     * Compare two shopping items to see if all their feels are identical to each other.
     * @param si1 ShoppingItem1
     * @param si2 ShoppingItem2
     * @return true if all corresponding fields of the shopping items are the same, false otherwise.
     */
    public static boolean areExactlySameShoppingItems(ShoppingItem si1, ShoppingItem si2) {
        return si1.isSameName(si2) && (si1.getAmount().toString().equals(si2.getAmount().toString()))
                && (si1.isBought() == si2.isBought()) && (si1.isUrgent() == si2.isUrgent());
    }

    @Test
    void isBought() {
        assertFalse(BANANA.isBought());
        System.out.println(BOUGHT_EGGS);
        assertTrue(BOUGHT_EGGS.isBought());
    }

    @Test
    void isUrgent() {
        assertFalse(APPLE.isUrgent());
        assertTrue(URGENT_EGGS.isUrgent());
    }

    @Test
    void setBought() {
        assertTrue(APPLE.setBought(true).isBought());
        assertFalse(BOUGHT_EGGS.setBought(false).isBought());
    }

    @Test
    void setUrgent() {
        assertFalse(URGENT_EGGS.setUrgent(false).isUrgent());
        assertTrue(CAKE.setUrgent(true).isUrgent());
    }

    @Test
    void getBoughtItem() {
        System.out.println(EGGS.getBoughtItem(new Amount("12units"), new ExpiryDate("27/12/2019")));
        System.out.println(new GroceryItem(new Name("Eggs"), new Amount("12units"), new ExpiryDate("27/12/2019"),
                new HashSet<>()));
        assertTrue(EGG.isSameFood(EGGS.getBoughtItem(new Amount("12units"), new ExpiryDate("03/12/2019"))));
        assertFalse(new GroceryItem(new Name("Cake"), new Amount("10g"), new ExpiryDate("27/11/2019"),
                new HashSet<>()).equals(CAKE.getBoughtItem(new Amount("10g"), new ExpiryDate("15/10/2019"))));
    }

    @Test
    void isCompletelyBought() {
        assertFalse(ShoppingItem.isCompletelyBought(APPLE, getTypicalBoughtList().getGroceryList()));
        assertTrue(ShoppingItem.isCompletelyBought(BOUGHT_EGGS, getTypicalBoughtList().getGroceryList()));
    }
}
