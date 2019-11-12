package io.xpire.model.item;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.DUCK;
import static io.xpire.testutil.TypicalItems.FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.XpireItemBuilder;

public class XpireItemTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        XpireItem xpireItem = new XpireItemBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> xpireItem.getTags().remove(0));
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(FISH.isSameItem(FISH));

        // null -> returns false
        assertFalse(FISH.isSameItem(null));

        // different expiry date -> returns false
        XpireItem editedFish = new XpireItemBuilder(FISH).withExpiryDate(VALID_EXPIRY_DATE_APPLE).build();
        assertFalse(FISH.isSameItem(editedFish));

        // different name -> returns false
        editedFish = new XpireItemBuilder(FISH).withName(VALID_NAME_APPLE).build();
        assertFalse(FISH.isSameItem(editedFish));

        // same name, same expiry date , different attributes -> returns true
        editedFish = new XpireItemBuilder(FISH).withExpiryDate(VALID_EXPIRY_DATE_FISH)
                                            .withTags(VALID_TAG_DRINK).build();
        assertTrue(FISH.isSameItem(editedFish));

    }

    @Test
    public void equals() {
        // same values -> returns true
        XpireItem kiwiCopy = new XpireItemBuilder(FISH).build();
        assertTrue(FISH.equals(kiwiCopy));

        // same object -> returns true
        assertTrue(FISH.equals(FISH));

        // null -> returns false
        assertFalse(FISH.equals(null));

        // different type -> returns false
        assertFalse(FISH.equals(5));

        // different xpireItem -> returns false
        assertFalse(FISH.equals(DUCK));

        // different name -> returns false
        XpireItem editedFish = new XpireItemBuilder(FISH).withName(VALID_NAME_BANANA).build();
        assertFalse(FISH.equals(editedFish));

        // different expiry date -> returns false
        editedFish = new XpireItemBuilder(FISH).withExpiryDate("01/01/2020").build();
        assertFalse(FISH.equals(editedFish));

        // different tags -> returns false
        editedFish = new XpireItemBuilder(FISH).withTags(VALID_TAG_DRINK).build();
        assertFalse(FISH.equals(editedFish));
    }
}
