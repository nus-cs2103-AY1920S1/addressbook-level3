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
        Assertions.assertTrue(TypicalDishes.ALICE.isSameDish(TypicalDishes.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalDishes.ALICE.isSameDish(null));

        // different name -> returns false
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalDishes.ALICE.isSameDish(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalDishes.ALICE.isSameDish(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Dish aliceCopy = new DishBuilder(TypicalDishes.ALICE).build();
        Assertions.assertTrue(TypicalDishes.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalDishes.ALICE.equals(TypicalDishes.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalDishes.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalDishes.ALICE.equals(5));

        // different dish -> returns false
        Assertions.assertFalse(TypicalDishes.ALICE.equals(TypicalDishes.BOB));

        // different name -> returns false
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalDishes.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalDishes.ALICE.equals(editedAlice));
    }
}
