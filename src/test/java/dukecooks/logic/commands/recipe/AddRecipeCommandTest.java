package dukecooks.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.Assert;
import dukecooks.testutil.recipe.RecipeBuilder;

public class AddRecipeCommandTest {

    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddRecipeCommand(null));
    }

    @Test
    public void execute_recipeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecipeAdded modelStub = new ModelStubAcceptingRecipeAdded();
        Recipe validRecipe = new RecipeBuilder().build();

        CommandResult commandResult = new AddRecipeCommand(validRecipe).execute(modelStub);

        assertEquals(String.format(AddRecipeCommand.MESSAGE_SUCCESS, validRecipe), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecipe), modelStub.recipesAdded);
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() {
        Recipe validRecipe = new RecipeBuilder().build();
        AddRecipeCommand addRecipeCommand = new AddRecipeCommand(validRecipe);
        ModelStub modelStub = new ModelStubWithRecipe(validRecipe);

        Assert.assertThrows(CommandException.class, AddRecipeCommand.MESSAGE_DUPLICATE_RECIPE, () -> addRecipeCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        Recipe alice = new RecipeBuilder().withName("Alice").build();
        Recipe bob = new RecipeBuilder().withName("Bob").build();
        AddRecipeCommand addAliceCommand = new AddRecipeCommand(alice);
        AddRecipeCommand addBobCommand = new AddRecipeCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddRecipeCommand addAliceCommandCopy = new AddRecipeCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single recipe.
     */
    private class ModelStubWithRecipe extends ModelStub {
        private final Recipe recipe;

        ModelStubWithRecipe(Recipe recipe) {
            requireNonNull(recipe);
            this.recipe = recipe;
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return this.recipe.isSameRecipe(recipe);
        }
    }

    /**
     * A Model stub that always accept the recipe being added.
     */
    private class ModelStubAcceptingRecipeAdded extends ModelStub {
        final ArrayList<Recipe> recipesAdded = new ArrayList<>();

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return recipesAdded.stream().anyMatch(recipe::isSameRecipe);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            requireNonNull(recipe);
            recipesAdded.add(recipe);
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            return new RecipeBook();
        }
    }

}
