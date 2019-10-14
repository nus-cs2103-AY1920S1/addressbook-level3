package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalDukeCooks;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.Recipe;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteRecipeCommand}.
 */
public class DeleteRecipeCommandTest {

    private Model model = new ModelManager(getTypicalDukeCooks(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDelete = model.getFilteredRecipeList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_PERSON_SUCCESS, recipeToDelete);

        ModelManager expectedModel = new ModelManager(model.getDukeCooks(), new UserPrefs());
        expectedModel.deleteRecipe(recipeToDelete);

        assertCommandSuccess(deleteRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecipeCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Recipe recipeToDelete = model.getFilteredRecipeList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_PERSON_SUCCESS, recipeToDelete);

        Model expectedModel = new ModelManager(model.getDukeCooks(), new UserPrefs());
        expectedModel.deleteRecipe(recipeToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDukeCooks().getRecipeList().size());

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecipeCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRecipeCommand deleteFirstCommand = new DeleteRecipeCommand(INDEX_FIRST_PERSON);
        DeleteRecipeCommand deleteSecondCommand = new DeleteRecipeCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecipeCommand deleteFirstCommandCopy = new DeleteRecipeCommand(INDEX_FIRST_PERSON);
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
    private void showNoPerson(Model model) {
        model.updateFilteredRecipeList(p -> false);

        assertTrue(model.getFilteredRecipeList().isEmpty());
    }
}
