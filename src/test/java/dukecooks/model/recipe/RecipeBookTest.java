package dukecooks.model.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.exceptions.DuplicateRecipeException;
import dukecooks.model.recipe.exceptions.RecipeNotFoundException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.recipe.RecipeBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecipeBookTest {

    private final RecipeBook recipeBook = new RecipeBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), recipeBook.getRecipeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> recipeBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyRecipeBook_replacesData() {
        RecipeBook newData = getTypicalRecipeBook();
        recipeBook.resetData(newData);
        assertEquals(newData, recipeBook);
    }

    @Test
    public void resetData_withDuplicateRecipes_throwsDuplicateRecipeException() {
        // Two recipes with the same identity fields
        Recipe editedMilo = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        List<Recipe> newRecipes = Arrays.asList(MILO, editedMilo);
        RecipeBookStub newData = new RecipeBookStub(newRecipes);

        Assert.assertThrows(DuplicateRecipeException.class, () -> recipeBook.resetData(newData));
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> recipeBook.hasRecipe(null));
    }

    @Test
    public void hasRecipe_recipeNotInRecipeBook_returnsFalse() {
        assertFalse(recipeBook.hasRecipe(MILO));
    }

    @Test
    public void hasRecipe_recipeInRecipeBook_returnsTrue() {
        recipeBook.addRecipe(MILO);
        assertTrue(recipeBook.hasRecipe(MILO));
    }

    @Test
    public void hasRecipe_recipeWithSameIdentityFieldsInRecipeBook_returnsTrue() {
        recipeBook.addRecipe(MILO);
        Recipe editedMilo = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        assertTrue(recipeBook.hasRecipe(editedMilo));
    }

    @Test
    public void retrieveRecipe_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> recipeBook.retrieveRecipe(null));
    }

    @Test
    public void retrieveRecipe_recipeNotInRecipeBook_throwsRecipeNotFoundException() {
        Assert.assertThrows(RecipeNotFoundException.class, () -> recipeBook.retrieveRecipe(MILO));
    }

    @Test
    public void retrieveRecipe_recipeInRecipeBook_success() {
        recipeBook.addRecipe(MILO);
        assertEquals(MILO, recipeBook.retrieveRecipe(MILO));
    }

    @Test
    public void retrieveRecipe_recipeWithSameIdentityFieldsInRecipeBook_returnsTrue() {
        recipeBook.addRecipe(MILO);
        Recipe editedMilo = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        assertEquals(MILO, recipeBook.retrieveRecipe(editedMilo));
    }

    @Test
    public void getRecipeList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> recipeBook.getRecipeList().remove(0));
    }

    /**
     * A stub ReadOnlyRecipeBook whose recipes list can violate interface constraints.
     */
    private static class RecipeBookStub implements ReadOnlyRecipeBook {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

        RecipeBookStub(Collection<Recipe> recipes) {
            this.recipes.setAll(recipes);
        }

        @Override
        public ObservableList<Recipe> getRecipeList() {
            return recipes;
        }
    }

    @Test
    public void testRecipeBookHashCode() {
        RecipeBook book1 = new RecipeBook(getTypicalRecipeBook());
        RecipeBook book2 = new RecipeBook(getTypicalRecipeBook());

        assertEquals(book1.hashCode(), book2.hashCode());
    }

}
