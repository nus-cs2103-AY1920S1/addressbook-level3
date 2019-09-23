package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFood.ALICE;
import static seedu.address.testutil.TypicalFood.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.testutil.FoodBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatefoods_throwsDuplicatefoodException() {
        // Two foods with the same identity fields
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Food> newFoods = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newFoods);

        assertThrows(DuplicateFoodException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasFood_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasFood(ALICE));
    }

    @Test
    public void hasFood_foodInAddressBook_returnsTrue() {
        addressBook.addFood(ALICE);
        assertTrue(addressBook.hasFood(ALICE));
    }

    @Test
    public void hasFood_foodWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addFood(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasFood(editedAlice));
    }

    @Test
    public void getFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose foods list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Food> foods = FXCollections.observableArrayList();

        AddressBookStub(Collection<Food> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return foods;
        }
    }

}
