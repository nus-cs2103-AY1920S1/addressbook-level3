package dukecooks.model.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.BURGER_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.FISH_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.MILO_MP;
import static dukecooks.testutil.recipe.TypicalRecipes.BURGER;
import static dukecooks.testutil.recipe.TypicalRecipes.FISH;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.testutil.Assert;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class MealPlanTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        MealPlan mealPlan = new MealPlanBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay1().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay2().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay3().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay4().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay5().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay6().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlan.getDay7().remove(0));
    }

    @Test
    public void isSameMealPlan() {
        // same object -> returns true
        assertTrue(MILO_MP.isSameMealPlan(MILO_MP));

        // null -> returns false
        assertFalse(MILO_MP.isSameMealPlan(null));

        // different name -> returns false
        MealPlan editedMiloMp = new MealPlanBuilder(MILO_MP).withName(CommandTestUtil.VALID_NAME_BURGER).build();
        assertFalse(MILO_MP.isSameMealPlan(editedMiloMp));

        // same name, different attributes -> returns true
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay1(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay2(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay3(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay4(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay5(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay6(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP)
                .withDay7(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO_MP.isSameMealPlan(editedMiloMp));
    }

    @Test
    public void replaceRecipe_oldRecipeNull_throwsNullExceptionError() {
        MealPlan mealPlan = new MealPlan(new MealPlanName("Test"));
        Assert.assertThrows(NullPointerException.class, () -> mealPlan.replaceRecipe(null, MILO));
    }

    @Test
    public void replaceRecipe_newRecipeNull_throwsNullExceptionError() {
        MealPlan mealPlan = new MealPlan(new MealPlanName("Test"));
        Assert.assertThrows(NullPointerException.class, () -> mealPlan.replaceRecipe(MILO, null));
    }

    @Test
    public void replaceRecipe_newRecipeNull_success() {
        MealPlan expectedMealPlan = new MealPlanBuilder(FISH_MP).withName(CommandTestUtil.VALID_NAME_BURGER_MP).build();
        MealPlan actualMealPlan = BURGER_MP;
        actualMealPlan.replaceRecipe(BURGER, FISH);
        assertEquals(expectedMealPlan, actualMealPlan);
    }

    @Test
    public void equals() {
        // same values -> returns true
        MealPlan aliceCopy = new MealPlanBuilder(MILO_MP).build();
        assertTrue(MILO_MP.equals(aliceCopy));

        // same object -> returns true
        assertTrue(MILO_MP.equals(MILO_MP));

        // null -> returns false
        assertFalse(MILO_MP.equals(null));

        // different type -> returns false
        assertFalse(MILO_MP.equals(5));

        // different mealPlan -> returns false
        assertFalse(MILO_MP.equals(BURGER_MP));

        // different name -> returns false
        MealPlan editedMiloMp = new MealPlanBuilder(MILO_MP).withName(CommandTestUtil.VALID_NAME_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));

        // different ingredients -> returns false
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay1(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay2(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay3(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay4(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay5(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay6(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
        editedMiloMp = new MealPlanBuilder(MILO_MP).withDay7(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO_MP.equals(editedMiloMp));
    }
}
