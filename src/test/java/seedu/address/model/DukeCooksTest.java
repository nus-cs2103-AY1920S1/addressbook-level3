package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_BURGER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.MILO;
import static seedu.address.testutil.TypicalRecipes.getTypicalDukeCooks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.testutil.RecipeBuilder;

public class DukeCooksTest {

    private final DukeCooks dukeCooks = new DukeCooks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dukeCooks.getRecipeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        DukeCooks newData = getTypicalDukeCooks();
        dukeCooks.resetData(newData);
        assertEquals(newData, dukeCooks);
    }

    @Test
    public void resetData_withDuplicateRecipes_throwsDuplicateRecipeException() {
        // Two recipes with the same identity fields
        Recipe editedMilo = new RecipeBuilder(MILO).withIngredients(VALID_INGREDIENT_BURGER)
                .build();
        List<Recipe> newRecipes = Arrays.asList(MILO, editedMilo);
        DukeCooksStub newData = new DukeCooksStub(newRecipes);

        assertThrows(DuplicateRecipeException.class, () -> dukeCooks.resetData(newData));
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.hasRecipe(null));
    }

    @Test
    public void hasRecipe_recipeNotInDukeCooks_returnsFalse() {
        assertFalse(dukeCooks.hasRecipe(MILO));
    }

    @Test
    public void hasRecipe_recipeInDukeCooks_returnsTrue() {
        dukeCooks.addRecipe(MILO);
        assertTrue(dukeCooks.hasRecipe(MILO));
    }

    @Test
    public void hasRecipe_recipeWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        dukeCooks.addRecipe(MILO);
        Recipe editedMilo = new RecipeBuilder(MILO).withIngredients(VALID_INGREDIENT_BURGER)
                .build();
        assertTrue(dukeCooks.hasRecipe(editedMilo));
    }

    @Test
    public void getRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> dukeCooks.getRecipeList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose recipes list can violate interface constraints.
     */
    private static class DukeCooksStub implements ReadOnlyDukeCooks {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

        DukeCooksStub(Collection<Recipe> recipes) {
            this.recipes.setAll(recipes);
        }

        @Override
        public ObservableList<Recipe> getRecipeList() {
            return recipes;
        }
    }

}
