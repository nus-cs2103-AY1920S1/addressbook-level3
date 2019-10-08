package calofit.model;

import calofit.model.meal.Dish;
import calofit.model.meal.exceptions.DuplicateDishException;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static calofit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.*;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getDishList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalDishes.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateDishes_throwsDuplicateDishesException() {
        // Two dishes with the same identity fields
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Dish> newDishes = Arrays.asList(TypicalDishes.ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newDishes);

        Assert.assertThrows(DuplicateDishException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasDish_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.hasDish(null));
    }

    @Test
    public void hasDish_dishNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDish(TypicalDishes.ALICE));
    }

    @Test
    public void hasDish_dishInAddressBook_returnsTrue() {
        addressBook.addDish(TypicalDishes.ALICE);
        assertTrue(addressBook.hasDish(TypicalDishes.ALICE));
    }

    @Test
    public void hasDish_dishWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addDish(TypicalDishes.ALICE);
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasDish(editedAlice));
    }

    @Test
    public void getDishList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> addressBook.getDishList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose dishes list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Dish> dishes = FXCollections.observableArrayList();

        AddressBookStub(Collection<Dish> dishes) {
            this.dishes.setAll(dishes);
        }

        @Override
        public ObservableList<Dish> getDishList() {
            return dishes;
        }
    }

}
