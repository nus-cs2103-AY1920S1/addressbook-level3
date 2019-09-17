package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.APPLE;
import static seedu.address.testutil.TypicalItems.getTypicalExpiryDateTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.testutil.ItemBuilder;

public class ExpiryDateTrackerTest {

    private final ExpiryDateTracker expiryDateTracker = new ExpiryDateTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expiryDateTracker.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expiryDateTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExpiryDateTracker_replacesData() {
        ExpiryDateTracker newData = getTypicalExpiryDateTracker();
        expiryDateTracker.resetData(newData);
        assertEquals(newData, expiryDateTracker);
    }

    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedAlice = new ItemBuilder(APPLE).withExpiryDate(VALID_EXPIRY_DATE_APPLE).withTags(VALID_TAG_FRUIT)
                                                   .build();
        List<Item> newPersons = Arrays.asList(APPLE, editedAlice);
        ExpiryDateTrackerStub newData = new ExpiryDateTrackerStub(newPersons);

        assertThrows(DuplicateItemException.class, () -> expiryDateTracker.resetData(newData));
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expiryDateTracker.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInExpiryDateTracker_returnsFalse() {
        assertFalse(expiryDateTracker.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemInExpiryDateTracker_returnsTrue() {
        expiryDateTracker.addItem(APPLE);
        assertTrue(expiryDateTracker.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInExpiryDateTracker_returnsTrue() {
        expiryDateTracker.addItem(APPLE);
        Item editedAlice = new ItemBuilder(APPLE).withExpiryDate(VALID_EXPIRY_DATE_APPLE).withTags(VALID_TAG_FRUIT)
                                                   .build();
        assertTrue(expiryDateTracker.hasItem(editedAlice));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> expiryDateTracker.getItemList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ExpiryDateTrackerStub implements ReadOnlyExpiryDateTracker {
        private final ObservableList<Item> items = FXCollections.observableArrayList();

        ExpiryDateTrackerStub(Collection<Item> items) {
            this.items.setAll(items);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }

}
