package calofit.model;

import calofit.model.meal.Meal;
import calofit.model.meal.exceptions.DuplicateMealException;
import calofit.testutil.Assert;
import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalMeals;
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
        assertEquals(Collections.emptyList(), addressBook.getMealList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalMeals.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateMeals_throwsDuplicateMealsException() {
        // Two meals with the same identity fields
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Meal> newMeals = Arrays.asList(TypicalMeals.ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newMeals);

        Assert.assertThrows(DuplicateMealException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasMeal_nullMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.hasMeal(null));
    }

    @Test
    public void hasMeal_mealNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasMeal(TypicalMeals.ALICE));
    }

    @Test
    public void hasMeal_mealInAddressBook_returnsTrue() {
        addressBook.addMeal(TypicalMeals.ALICE);
        assertTrue(addressBook.hasMeal(TypicalMeals.ALICE));
    }

    @Test
    public void hasMeal_mealWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addMeal(TypicalMeals.ALICE);
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasMeal(editedAlice));
    }

    @Test
    public void getMealList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> addressBook.getMealList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose meals list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Meal> meals = FXCollections.observableArrayList();

        AddressBookStub(Collection<Meal> meals) {
            this.meals.setAll(meals);
        }

        @Override
        public ObservableList<Meal> getMealList() {
            return meals;
        }
    }

}
