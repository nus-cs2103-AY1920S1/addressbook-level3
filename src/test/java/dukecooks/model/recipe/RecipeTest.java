package dukecooks.model.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.BURGER;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.Assert;
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

    @Test
    public void testRecipeHashCode() {
        RecipeName name = new RecipeName("A");
        Ingredient ingredient = new Ingredient("B");
        Set<Ingredient> ingredients = new HashSet<>();
        Calories calories = new Calories("0");
        Carbs carbs = new Carbs("0");
        Fats fats = new Fats("0");
        Protein protein = new Protein("0");
        ingredients.add(ingredient);
        Recipe recipe1 = new Recipe(name, ingredients, calories, carbs, fats, protein);
        Recipe recipe2 = new Recipe(name, ingredients, calories, carbs, fats, protein);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());
    }
}
