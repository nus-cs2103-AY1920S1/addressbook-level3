package calofit.model.dish;

import static calofit.logic.commands.CommandTestUtil.VALID_TAG_SALTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;

import calofit.model.dish.exceptions.DuplicateDishException;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class DishDatabaseTest {

    private final DishDatabase dishDatabase = new DishDatabase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dishDatabase.getDishList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> dishDatabase.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDishDatabase_replacesData() {
        DishDatabase newData = TypicalDishes.getTypicalDishDatabase();
        dishDatabase.resetData(newData);
        assertEquals(newData, dishDatabase);
    }

    @Test
    public void resetData_withDuplicateDishes_throwsDuplicateDishesException() {
        // Two dishes with the same identity fields
        Dish editedSpaghetti = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(VALID_TAG_SALTY)
                .build();
        List<Dish> newDishes = Arrays.asList(TypicalDishes.SPAGHETTI, editedSpaghetti);
        DishDatabaseStub newData = new DishDatabaseStub(newDishes);

        Assert.assertThrows(DuplicateDishException.class, () -> dishDatabase.resetData(newData));
    }

    @Test
    public void hasDish_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> dishDatabase.hasDish(null));
    }

    @Test
    public void hasDish_dishNotInDishDatabase_returnsFalse() {
        assertFalse(dishDatabase.hasDish(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void hasDish_dishInDishDatabase_returnsTrue() {
        dishDatabase.addDish(TypicalDishes.SPAGHETTI);
        assertTrue(dishDatabase.hasDish(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void hasDish_dishWithSameIdentityFieldsInDishDatabase_returnsTrue() {
        dishDatabase.addDish(TypicalDishes.SPAGHETTI);
        Dish editedSpaghetti = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(VALID_TAG_SALTY)
                .build();
        assertTrue(dishDatabase.hasDish(editedSpaghetti));
    }

    @Test
    public void getDishList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> dishDatabase.getDishList().remove(0));
    }

    /**
     * A stub ReadOnlyDishDatabase whose dishes list can violate interface constraints.
     */
    private static class DishDatabaseStub implements ReadOnlyDishDatabase {
        private final ObservableList<Dish> dishes = FXCollections.observableArrayList();

        DishDatabaseStub(Collection<Dish> dishes) {
            this.dishes.setAll(dishes);
        }

        @Override
        public ObservableList<Dish> getDishList() {
            return dishes;
        }
    }

}
