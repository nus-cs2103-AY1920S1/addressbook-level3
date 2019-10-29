package calofit.model.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import calofit.logic.commands.CommandTestUtil;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class DishTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Dish dish = new DishBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> dish.getTags().remove(0));
    }

    @Test
    public void isSameDish() {
        // same object -> returns true
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.isSameDish(TypicalDishes.SPAGHETTI));

        // null -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.isSameDish(null));

        // different name -> returns false
        Dish editedDish = new DishBuilder(
                TypicalDishes.SPAGHETTI).withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.isSameDish(editedDish));

        // same name, same phone, different attributes -> returns true
        editedDish = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY).build();
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.isSameDish(editedDish));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Dish spaghettiCopy = new DishBuilder(TypicalDishes.SPAGHETTI).build();
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.equals(spaghettiCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.equals(TypicalDishes.SPAGHETTI));

        // null -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(5));

        // different dish -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(TypicalDishes.MACARONI));

        // different name -> returns false
        Dish editedDish = new DishBuilder(
                TypicalDishes.SPAGHETTI).withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(editedDish));

        // different tags -> returns false
        editedDish = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(editedDish));
    }
}
