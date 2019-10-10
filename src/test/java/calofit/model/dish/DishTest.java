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
        Dish editedAlice = new DishBuilder(
                TypicalDishes.SPAGHETTI).withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.isSameDish(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY).build();
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.isSameDish(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Dish aliceCopy = new DishBuilder(TypicalDishes.SPAGHETTI).build();
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalDishes.SPAGHETTI.equals(TypicalDishes.SPAGHETTI));

        // null -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(5));

        // different dish -> returns false
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(TypicalDishes.MACARONI));

        // different name -> returns false
        Dish editedAlice = new DishBuilder(
                TypicalDishes.SPAGHETTI).withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY).build();
        Assertions.assertFalse(TypicalDishes.SPAGHETTI.equals(editedAlice));
    }
}
