package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelHistory;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;

public class DeleteBudgetByIndexCommandTest {
    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void run_validIndexUnfilteredList_success() {
        Budget budgetToDelete = model.getFilteredBudgetList().get(INDEX_SECOND.getZeroBased());
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(INDEX_SECOND);

        String expectedMessage = String.format(DeleteBudgetByIndexCommand.MESSAGE_DELETE_BUDGET_SUCCESS,
                budgetToDelete);

        ModelManager expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");
        expectedModel.deleteBudget(budgetToDelete);

        assertCommandSuccess(deleteBudgetByIndexCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteBudgetByIndexCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
    }

    @Test
    public void run_onlyDefaultBudgetLeft_throwsCommandException() {
        Model emptyModel = new ModelManager(new MooLah(), new UserPrefs(), new ModelHistory());
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);

        assertCommandFailure(deleteBudgetByIndexCommand, emptyModel, Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
    }

    @Test
    public void run_tryingToDeleteDefaultBudget_throwsCommandException() {
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);

        assertCommandFailure(deleteBudgetByIndexCommand, model, Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
    }


    @Test
    public void equals() {
        DeleteBudgetByIndexCommand deleteFirstCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);
        DeleteBudgetByIndexCommand deleteSecondCommand = new DeleteBudgetByIndexCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBudgetByIndexCommand deleteFirstCommandCopy = new DeleteBudgetByIndexCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

