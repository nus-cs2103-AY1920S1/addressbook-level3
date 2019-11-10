package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.guilttrip.logic.commands.CommandTestUtil.showIncomeAtIndex;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.deletecommands.DeleteIncomeCommand;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
//import seedu.guilttrip.model.entry.Income;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteIncomeCommand}.
 */
public class DeleteIncomeCommandTest {

    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /*@Test
    public void execute_validIndexUnfilteredIncomeList_success() {
        Income incomeToDelete = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteIncomeCommand.MESSAGE_DELETE_ENTRY_SUCCESS, incomeToDelete);

        ModelManager expectedModel = new ModelManager(model.getGuiltTrip(), new UserPrefs());
        expectedModel.deleteIncome(incomeToDelete);

        assertCommandSuccess(deleteIncomeCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    @Test
    public void execute_invalidIndexUnfilteredIncomeList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIncomes().size() + 1);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteIncomeCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX,
                commandHistory);
    }

    /*@Test
    public void execute_validIndexFilteredIncomeList_success() {
        showIncomeAtIndex(model, INDEX_FIRST_ENTRY);

        Income incomeToDelete = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteIncomeCommand.MESSAGE_DELETE_ENTRY_SUCCESS, incomeToDelete);

        Model expectedModel = new ModelManager(model.getGuiltTrip(), new UserPrefs());
        expectedModel.deleteIncome(incomeToDelete);
        expectedModel.commitGuiltTrip();
        showNoIncome(expectedModel);

        assertCommandSuccess(deleteIncomeCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    @Test
    public void execute_invalidIndexFilteredIncomeList_throwsCommandException() {
        showIncomeAtIndex(model, INDEX_FIRST_ENTRY);

        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of GuiltTrip list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGuiltTrip().getIncomeList().size());

        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteIncomeCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX,
                commandHistory);
    }

    @Test
    public void equals() {
        DeleteIncomeCommand deleteFirstCommand = new DeleteIncomeCommand(INDEX_FIRST_ENTRY);
        DeleteIncomeCommand deleteSecondCommand = new DeleteIncomeCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteIncomeCommand deleteFirstCommandCopy = new DeleteIncomeCommand(INDEX_FIRST_ENTRY);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> not equals returns true
        assertNotEquals(1, deleteFirstCommand);

        // null -> not equals returns false
        assertNotEquals(null, deleteFirstCommand);

        // different entry -> not equals returns true
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no income.
     */
    private void showNoIncome(Model model) {
        model.updateFilteredIncomes(p -> false);

        assertTrue(model.getFilteredIncomes().isEmpty());
    }
}

