package calofit.logic.commands;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.meal.Meal;
import calofit.testutil.TypicalIndexes;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;

import static calofit.logic.commands.CommandTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Meal mealToDelete = model.getFilteredMealList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS, mealToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeal(mealToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMealList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Meal mealToDelete = model.getFilteredMealList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS, mealToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeal(mealToDelete);
        showNoMeal(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEAL;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMealList().size());

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

        // different meal -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeal(Model model) {
        model.updateFilteredMealList(p -> false);

        assertTrue(model.getFilteredMealList().isEmpty());
    }
}
