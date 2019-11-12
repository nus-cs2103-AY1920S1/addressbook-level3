package io.xpire.model;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.BAGEL;
import static io.xpire.testutil.TypicalItems.WATERMELON;
import static io.xpire.testutil.TypicalItems.getTypicalReplenishList;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_WATERMELON;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_SWEET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.item.Item;
import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.testutil.ItemBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReplenishListTest {

    private final ReplenishList replenishList = new ReplenishList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), replenishList.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> replenishList.resetData(null));
    }

    @Test
    public void resetData_withValidReplenishList_replacesData() {
        ReplenishList newData = getTypicalReplenishList();
        replenishList.resetData(newData);
        assertEquals(newData.getItemList(), replenishList.getItemList());
    }

    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        Item bagel = new ItemBuilder(BAGEL).build();
        List<Item> newReplenishItems = Arrays.asList(BAGEL, bagel);
        ReplenishListTest.ReplenishListStub newData = new ReplenishListTest.ReplenishListStub(newReplenishItems);
        assertThrows(DuplicateItemException.class, () -> replenishList.resetData(newData));
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> replenishList.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInReplenishList_returnsFalse() {
        assertFalse(replenishList.hasItem(WATERMELON));
    }

    @Test
    public void hasItem_itemInReplenishList_returnsTrue() {
        replenishList.addItem(BAGEL);
        assertTrue(replenishList.hasItem(BAGEL));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInReplenishList_returnsTrue() {
        replenishList.addItem(WATERMELON);
        Item editedWatermelon = new ItemBuilder().withName(VALID_NAME_WATERMELON).withTags(VALID_TAG_SWEET).build();
        assertTrue(replenishList.hasItem(editedWatermelon));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> replenishList.getItemList().remove(0));
    }

    /**
     * A stub ReadOnlyListView whose Items list can violate interface constraints.
     */
    private static class ReplenishListStub implements ReadOnlyListView {
        private final ObservableList<Item> replenishItems = FXCollections.observableArrayList();

        ReplenishListStub(Collection<Item> replenishItems) {
            this.replenishItems.setAll(replenishItems);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return replenishItems;
        }
    }
}
