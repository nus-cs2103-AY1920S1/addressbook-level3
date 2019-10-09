package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.savenus.testutil.TypicalFood.getTypicalAddressBook;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.food.Food;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FOOD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FOOD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        Model expectedModel = new ModelManager(model.getMenu(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);
        showNoFood(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Index outOfBoundIndex = INDEX_SECOND_FOOD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMenu().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_FOOD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_FOOD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_FOOD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFood(Model model) {
        model.updateFilteredFoodList(p -> false);

        assertTrue(model.getFilteredFoodList().isEmpty());
    }
}
