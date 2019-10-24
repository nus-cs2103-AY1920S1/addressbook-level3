package dukecooks.logic.commands.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteRecipeCommand}.
 */
public class DeleteRecipeCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDelete = model.getFilteredRecipeList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDelete);

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs());
        expectedModel.deleteRecipe(recipeToDelete);

        CommandTestUtil.assertCommandSuccess(deleteRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(outOfBoundIndex);

        CommandTestUtil.assertRecipeCommandFailure(deleteRecipeCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        Recipe recipeToDelete = model.getFilteredRecipeList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDelete);

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs());
        expectedModel.deleteRecipe(recipeToDelete);
        showNoRecipe(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of RecipeBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(outOfBoundIndex);

        CommandTestUtil.assertRecipeCommandFailure(deleteRecipeCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRecipeCommand deleteFirstCommand = new DeleteRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE);
        DeleteRecipeCommand deleteSecondCommand = new DeleteRecipeCommand(TypicalIndexes.INDEX_SECOND_RECIPE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecipeCommand deleteFirstCommandCopy = new DeleteRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecipe(Model model) {
        model.updateFilteredRecipeList(p -> false);

        assertTrue(model.getFilteredRecipeList().isEmpty());
    }
}
