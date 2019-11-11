package io.xpire.model;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.APPLE;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.testutil.XpireItemBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class XpireTest {

    private final Xpire xpire = new Xpire();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), xpire.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xpire.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExpiryDateTracker_replacesData() {
        Xpire newData = getTypicalExpiryDateTracker();
        xpire.resetData(newData);
        assertEquals(newData.getItemList(), xpire.getItemList());
    }

    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        XpireItem editedApple = new XpireItemBuilder(APPLE).withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                                         .withQuantity("1").build();
        List<XpireItem> newXpireItems = Arrays.asList(APPLE, editedApple);
        XpireStub newData = new XpireStub(newXpireItems);
        assertThrows(DuplicateItemException.class, () -> xpire.resetData(newData));
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xpire.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInExpiryDateTracker_returnsFalse() {
        assertFalse(xpire.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemInExpiryDateTracker_returnsTrue() {
        xpire.addItem(APPLE);
        assertTrue(xpire.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInExpiryDateTracker_returnsTrue() {
        xpire.addItem(APPLE);
        XpireItem editedApple = new XpireItemBuilder(APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withTags(VALID_TAG_FRUIT)
                .build();
        assertTrue(xpire.hasItem(editedApple));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> xpire.getItemList().remove(0));
    }

    /**
     * A stub ReadOnlyListView whose xpireItems list can violate interface constraints.
     */
    private static class XpireStub implements ReadOnlyListView {
        private final ObservableList<XpireItem> xpireItems = FXCollections.observableArrayList();

        XpireStub(Collection<XpireItem> xpireItems) {
            this.xpireItems.setAll(xpireItems);
        }

        @Override
        public ObservableList<XpireItem> getItemList() {
            return xpireItems;
        }
    }

}
