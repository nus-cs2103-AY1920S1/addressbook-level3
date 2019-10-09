package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalFood.ALICE;
import static seedu.savenus.testutil.TypicalFood.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.exceptions.DuplicateFoodException;
import seedu.savenus.testutil.FoodBuilder;

public class AddressBookTest {

    private final Menu menu = new Menu();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), menu.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> menu.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Menu newData = getTypicalAddressBook();
        menu.resetData(newData);
        assertEquals(newData, menu);
    }

    @Test
    public void resetData_withDuplicatefoods_throwsDuplicatefoodException() {
        // Two foods with the same identity fields
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Food> newFoods = Arrays.asList(ALICE, editedAlice);
        MenuStub newData = new MenuStub(newFoods);

        assertThrows(DuplicateFoodException.class, () -> menu.resetData(newData));
    }

    @Test
    public void hasFood_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> menu.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInAddressBook_returnsFalse() {
        assertFalse(menu.hasFood(ALICE));
    }

    @Test
    public void hasFood_foodInAddressBook_returnsTrue() {
        menu.addFood(ALICE);
        assertTrue(menu.hasFood(ALICE));
    }

    @Test
    public void hasFood_foodWithSameIdentityFieldsInAddressBook_returnsTrue() {
        menu.addFood(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(menu.hasFood(editedAlice));
    }

    @Test
    public void getFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> menu.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose foods list can violate interface constraints.
     */
    private static class MenuStub implements ReadOnlyMenu {
        private final ObservableList<Food> foods = FXCollections.observableArrayList();

        MenuStub(Collection<Food> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return foods;
        }
    }

}
