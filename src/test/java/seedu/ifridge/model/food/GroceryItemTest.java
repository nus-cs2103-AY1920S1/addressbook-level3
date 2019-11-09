package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_TAG_NUTS;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;

import org.junit.jupiter.api.Test;

import seedu.ifridge.testutil.GroceryItemBuilder;

public class GroceryItemTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        GroceryItem groceryItem = new GroceryItemBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> groceryItem.getTags().remove(0));
    }

    @Test
    public void isSameGroceryItem() {
        // same object -> returns true
        assertTrue(BANANA.isSameFood(BANANA));

        // null -> returns false
        assertFalse(BANANA.isSameFood(null));

        // different name -> returns false
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withName(VALID_NAME_NUTS).build();
        assertFalse(BANANA.isSameFood(editedBanana));

        // same name, same expiry date, but different tags -> returns true
        editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_NAME_NUTS).build();
        assertTrue(BANANA.isSameFood(editedBanana));

        // same name, same phone, same email, different attributes -> returns true
        editedBanana = new GroceryItemBuilder(BANANA).withAmount(VALID_AMOUNT_NUTS).withTags(VALID_NAME_NUTS).build();
        assertTrue(BANANA.isSameFood(editedBanana));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(BANANA.equals(BANANA));

        // same values -> returns true
        GroceryItem bananaCopy = new GroceryItemBuilder(BANANA).build();
        assertTrue(BANANA.equals(bananaCopy));

        // null -> returns false
        assertFalse(BANANA.equals(null));

        // different type -> returns false
        assertFalse(BANANA.equals(5));

        // different person -> returns false
        assertFalse(BANANA.equals(SPAGHETTI));

        // different name -> returns false
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withName(VALID_NAME_NUTS).build();
        assertFalse(BANANA.equals(editedBanana));

        // different tags -> returns false
        editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_NUTS).build();
        assertFalse(BANANA.equals(editedBanana));
    }
}
