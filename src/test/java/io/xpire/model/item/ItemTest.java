package io.xpire.model.item;

import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_DRINK;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.APPLE;
import static io.xpire.testutil.TypicalItems.KIWI;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.ItemBuilder;

public class ItemTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Item item = new ItemBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> item.getTags().remove(0));
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(KIWI.isSameItem(KIWI));

        // null -> returns false
        assertFalse(KIWI.isSameItem(null));

        // different expiry date -> returns false
        Item editedKiwi = new ItemBuilder(KIWI).withExpiryDate("02/02/2019").build();
        assertFalse(KIWI.isSameItem(editedKiwi));

        // different name -> returns false
        editedKiwi = new ItemBuilder(KIWI).withName(VALID_NAME_APPLE).build();
        assertFalse(KIWI.isSameItem(editedKiwi));

        // same name, same , different attributes -> returns true
        editedKiwi = new ItemBuilder(KIWI).withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                                            .withTags(VALID_TAG_DRINK).build();
        assertTrue(KIWI.isSameItem(editedKiwi));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Item kiwiCopy = new ItemBuilder(KIWI).build();
        assertTrue(KIWI.equals(kiwiCopy));

        // same object -> returns true
        assertTrue(KIWI.equals(KIWI));

        // null -> returns false
        assertFalse(KIWI.equals(null));

        // different type -> returns false
        assertFalse(KIWI.equals(5));

        // different item -> returns false
        assertFalse(KIWI.equals(APPLE));

        // different name -> returns false
        Item editedKiwi = new ItemBuilder(KIWI).withName(VALID_NAME_BANANA).build();
        assertFalse(KIWI.equals(editedKiwi));

        // different expiry date -> returns false
        editedKiwi = new ItemBuilder(KIWI).withExpiryDate("01/01/2019").build();
        assertFalse(KIWI.equals(editedKiwi));

        // different tags -> returns false
        editedKiwi = new ItemBuilder(KIWI).withTags(VALID_TAG_DRINK).build();
        assertFalse(KIWI.equals(editedKiwi));
    }
}
