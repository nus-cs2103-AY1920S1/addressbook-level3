package calofit.logic.commands;

import static calofit.logic.commands.CommandTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.dish.Dish;
import calofit.testutil.TypicalDishes;
import calofit.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalDishes.getTypicalDishDatabase(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Dish dishToDelete = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS, dishToDelete);

        ModelManager expectedModel = new ModelManager(model.getDishDatabase(), new UserPrefs());
        expectedModel.deleteDish(dishToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDishList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Dish dishToDelete = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS, dishToDelete);

        Model expectedModel = new ModelManager(model.getDishDatabase(), new UserPrefs());
        expectedModel.deleteDish(dishToDelete);
        showNoDish(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEAL;
        // ensures that outOfBoundIndex is still in bounds of dish database list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDishDatabase().getDishList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_MEAL);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different dish -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDish(Model model) {
        model.updateFilteredDishList(p -> false);

        assertTrue(model.getFilteredDishList().isEmpty());
    }
}
