package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalShoppingList.CAKE;
import static seedu.ifridge.testutil.TypicalShoppingList.EGGS;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.exceptions.DuplicateShoppingItemException;
import seedu.ifridge.testutil.ShoppingItemBuilder;

public class ShoppingListTest {

    private final ShoppingList shoppingList = new ShoppingList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), shoppingList.getShoppingList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> shoppingList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyShoppingList_replacesData() {
        ShoppingList newData = getTypicalShoppingList();
        shoppingList.resetData(newData);
        assertEquals(newData, shoppingList);
    }

    @Test
    public void resetData_withDuplicateShoppingItems_throwsDuplicateShoppingItemException() {
        // Two shoppingItems with the same identity fields
        ShoppingItem editedCake = new ShoppingItemBuilder(CAKE).withUrgent(true).build();
        List<ShoppingItem> newFoods = Arrays.asList(CAKE, editedCake);
        ShoppingListStub newData = new ShoppingListStub(newFoods);

        assertThrows(DuplicateShoppingItemException.class, () -> shoppingList.resetData(newData));
    }

    @Test
    public void hasShoppingItem_nullShoppingItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> shoppingList.hasShoppingItem(null));
    }

    @Test
    public void hasShoppingItem_shoppingItemNotInShoppingList_returnsFalse() {
        assertFalse(shoppingList.hasShoppingItem(CAKE));
    }

    @Test
    public void hasShoppingItem_shoppingItemInShoppingList_returnsTrue() {
        shoppingList.addShoppingItem(CAKE);
        assertTrue(shoppingList.hasShoppingItem(CAKE));
    }

    @Test
    public void hasShoppingItem_shoppingItemWithSameIdentityFieldsInShoppingList_returnsTrue() {
        shoppingList.addShoppingItem(EGGS);
        ShoppingItem editedEggs = new ShoppingItemBuilder().withName("Eggs")
                .build();
        assertTrue(shoppingList.hasShoppingItem(editedEggs));
    }

    @Test
    public void getShoppingItem_nullShoppingItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> shoppingList.getShoppingItem(null));
    }

    @Test
    public void getShoppingItem_shoppingItemExists_success() {
        shoppingList.addShoppingItem(EGGS);
        assertTrue(EGGS.isSameFood(shoppingList.getShoppingItem(EGGS)));
    }

    @Test
    public void getShoppingItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> shoppingList.getShoppingList().remove(0));
    }

    @Test
    public void containsShoppingItem_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> shoppingList.containsShoppingItemWithName(null));
    }

    @Test
    public void containsShoppingItem_successAndFailureTest() {
        ShoppingList shoppingList = getTypicalShoppingList();
        assertTrue(shoppingList.containsShoppingItemWithName(EGGS));
        assertFalse(shoppingList.containsShoppingItemWithName(new ShoppingItemBuilder().withName("nonExistent")
            .withAmount("15g").build()));
    }

    /**
     * A stub ReadOnlyShoppingList whose shoppingItems list can violate interface constraints.
     */
    private static class ShoppingListStub implements ReadOnlyShoppingList {
        private final ObservableList<ShoppingItem> foods = FXCollections.observableArrayList();

        ShoppingListStub(Collection<ShoppingItem> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<ShoppingItem> getShoppingList() {
            return foods;
        }

        @Override
        public boolean hasShoppingItem(ShoppingItem shoppingItem) {
            return foods.contains(shoppingItem);
        }
    }

}
