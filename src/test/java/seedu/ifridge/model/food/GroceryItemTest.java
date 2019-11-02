package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;

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

    // NOT SURE IF WE SHOULD CHECK FOR THIS SINCE WE DON'T EVEN CALL THE METHOD EQUALS AT ALL.
    /*@Test
    public void equals() {
        // same object -> returns true
        assertEquals(BANANA, BANANA);

        // same values -> returns true
        GroceryItem bananaCopy = new GroceryItemBuilder(BANANA).build();
        assertNotEquals(BANANA, bananaCopy);


        // null -> returns false
        assertNotEquals(null, BANANA);

        // different type -> returns false
        assertNotEquals(5, BANANA);

        // different person -> returns false
        assertNotEquals(BANANA, SPAGHETTI);

        // different name -> returns false
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withName(VALID_NAME_NUTS).build();
        assertNotEquals(BANANA, editedBanana);

        // different tags -> returns false
        editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_NUTS).build();
        assertNotEquals(BANANA, editedBanana);
    }*/
}
