package io.xpire.model.item;

import static io.xpire.testutil.TypicalItems.BAGEL;
import static io.xpire.testutil.TypicalItems.BISCUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_PAPAYA;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_BREAKFAST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertTrue(BAGEL.isSameItem(BAGEL));

        // null -> returns false
        assertFalse(BAGEL.isSameItem(null));

        // different name -> returns false
        Item editedBagel = new ItemBuilder(BAGEL).withName(VALID_NAME_PAPAYA).build();
        assertFalse(BAGEL.isSameItem(editedBagel));

        // same name, different tags -> returns true
        editedBagel = new ItemBuilder(BAGEL).withTags(VALID_TAG_BREAKFAST).build();
        assertTrue(BAGEL.isSameItem(editedBagel));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Item bagelCopy = new ItemBuilder(BAGEL).build();
        assertTrue(BAGEL.equals(bagelCopy));

        // same object -> returns true
        assertTrue(BAGEL.equals(BAGEL));

        // null -> returns false
        assertFalse(BAGEL.equals(null));

        // different type -> returns false
        assertFalse(BAGEL.equals(5));

        // different item -> returns false
        assertFalse(BAGEL.equals(BISCUIT));

        // different name -> returns false
        Item editedBagel = new ItemBuilder(BAGEL).withName(VALID_NAME_PAPAYA).build();
        assertFalse(BAGEL.isSameItem(editedBagel));

        // different tags -> returns false
        editedBagel = new ItemBuilder(BAGEL).withTags(VALID_TAG_BREAKFAST).build();
        assertFalse(BAGEL.equals(editedBagel));
    }
}
