package calofit.model.meal;

import calofit.logic.commands.CommandTestUtil;
import calofit.testutil.Assert;
import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MealTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Meal meal = new MealBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> meal.getTags().remove(0));
    }

    @Test
    public void isSameMeal() {
        // same object -> returns true
        Assertions.assertTrue(TypicalMeals.ALICE.isSameMeal(TypicalMeals.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalMeals.ALICE.isSameMeal(null));

        // different name -> returns false
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalMeals.ALICE.isSameMeal(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalMeals.ALICE.isSameMeal(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meal aliceCopy = new MealBuilder(TypicalMeals.ALICE).build();
        Assertions.assertTrue(TypicalMeals.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalMeals.ALICE.equals(TypicalMeals.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalMeals.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalMeals.ALICE.equals(5));

        // different meal -> returns false
        Assertions.assertFalse(TypicalMeals.ALICE.equals(TypicalMeals.BOB));

        // different name -> returns false
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalMeals.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalMeals.ALICE.equals(editedAlice));
    }
}
