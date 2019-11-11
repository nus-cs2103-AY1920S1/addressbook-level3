package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_TAG_BANANA;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.exceptions.DuplicateFoodException;
import seedu.ifridge.testutil.GroceryItemBuilder;

public class GroceryListTest {

    private final GroceryList groceryList = new GroceryList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), groceryList.getGroceryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groceryList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroceryList_replacesData() {
        GroceryList newData = getTypicalGroceryList();
        groceryList.resetData(newData);
        assertEquals(newData, groceryList);
    }

    @Test
    public void resetData_withDuplicateGroceryItems_throwsDuplicateFoodException() {
        // Two grocery items with the same identity fields
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_BANANA)
                .build();
        List<GroceryItem> newFoods = Arrays.asList(BANANA, editedBanana);
        GroceryListStub newData = new GroceryListStub(newFoods);

        assertThrows(DuplicateFoodException.class, () -> groceryList.resetData(newData));
    }

    @Test
    public void hasGroceryItem_nullGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groceryList.hasGroceryItem(null));
    }

    @Test
    public void hasGroceryItem_groceryItemNotInGroceryList_returnsFalse() {
        assertFalse(groceryList.hasGroceryItem(SPAGHETTI));
    }

    @Test
    public void hasGroceryItem_groceryItemInGroceryList_returnsTrue() {
        groceryList.addGroceryItem(SPAGHETTI);
        assertTrue(groceryList.hasGroceryItem(SPAGHETTI));
    }

    @Test
    public void hasGroceryItem_groceryItemWithSameIdentityFieldsInGroceryList_returnsTrue() {
        groceryList.addGroceryItem(BANANA);
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_BANANA)
                .build();
        assertTrue(groceryList.hasGroceryItem(editedBanana));
    }

    @Test
    public void getGroceryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> groceryList.getGroceryList().remove(0));
    }

    /**
     * A stub ReadOnlyGroceryList whose persons list can violate interface constraints.
     */
    private static class GroceryListStub implements ReadOnlyGroceryList {
        private final ObservableList<GroceryItem> groceryList = FXCollections.observableArrayList();

        GroceryListStub(Collection<GroceryItem> groceryList) {
            this.groceryList.setAll(groceryList);
        }

        @Override
        public ObservableList<GroceryItem> getGroceryList() {
            return groceryList;
        }
    }

}
