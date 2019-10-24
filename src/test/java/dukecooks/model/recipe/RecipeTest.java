package dukecooks.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dukecooks.testutil.Assert.assertThrows;
import static dukecooks.testutil.recipe.TypicalRecipes.BURGER;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.Assert;
import org.junit.jupiter.api.Test;

import dukecooks.testutil.recipe.RecipeBuilder;

public class RecipeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new RecipeBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> recipe.getIngredients().remove(0));
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(MILO.isSameRecipe(MILO));

        // null -> returns false
        assertFalse(MILO.isSameRecipe(null));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(MILO).withName(CommandTestUtil.VALID_NAME_BURGER).build();
        assertFalse(MILO.isSameRecipe(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new RecipeBuilder(MILO)
                .withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO.isSameRecipe(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new RecipeBuilder(MILO).build();
        assertTrue(MILO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(MILO.equals(MILO));

        // null -> returns false
        assertFalse(MILO.equals(null));

        // different type -> returns false
        assertFalse(MILO.equals(5));

        // different recipe -> returns false
        assertFalse(MILO.equals(BURGER));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(MILO).withName(CommandTestUtil.VALID_NAME_BURGER).build();
        assertFalse(MILO.equals(editedAlice));

        // different ingredients -> returns false
        editedAlice = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO.equals(editedAlice));
    }
}
