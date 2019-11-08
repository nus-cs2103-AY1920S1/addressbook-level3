package seedu.ichifund.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.logic.commands.CommandTestUtil.showBudgetAtIndex;
import static seedu.ichifund.testutil.TypicalFundBook.getTypicalFundBook;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;
import seedu.ichifund.model.budget.Budget;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteBudgetCommand}.
 */
public class DeleteBudgetCommandTest {

    private Model model = new ModelManager(getTypicalFundBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Budget budgetToDelete = model.getFilteredBudgetList().get(INDEX_FIRST.getZeroBased());
        DeleteBudgetCommand deleteCommand = new DeleteBudgetCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete);

        ModelManager expectedModel = new ModelManager(model.getFundBook(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
        DeleteBudgetCommand deleteCommand = new DeleteBudgetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBudgetAtIndex(model, INDEX_FIRST);

        Budget budgetToDelete = model.getFilteredBudgetList().get(INDEX_FIRST.getZeroBased());
        DeleteBudgetCommand deleteCommand = new DeleteBudgetCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete);

        Model expectedModel = new ModelManager(model.getFundBook(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);
        showNoBudget(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBudgetAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of fund book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFundBook().getBudgetList().size());

        DeleteBudgetCommand deleteCommand = new DeleteBudgetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteBudgetCommand deleteFirstCommand = new DeleteBudgetCommand(INDEX_FIRST);
        DeleteBudgetCommand deleteSecondCommand = new DeleteBudgetCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBudgetCommand deleteFirstCommandCopy = new DeleteBudgetCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different budget -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBudget(Model model) {
        model.updateFilteredBudgetList(p -> false);

        assertTrue(model.getFilteredBudgetList().isEmpty());
    }
}
