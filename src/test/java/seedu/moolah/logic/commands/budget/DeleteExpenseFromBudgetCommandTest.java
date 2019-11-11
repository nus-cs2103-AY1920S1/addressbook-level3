package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Expense;

public class DeleteExpenseFromBudgetCommandTest {
    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void run_validIndex_success() {
        assertEquals(model.getPrimaryBudget().getDescription().fullDescription, "School related expenses");
        assertTrue(model.getPrimaryBudget().getExpenses().size() == 2);
        Expense expenseToDelete = model.getPrimaryBudget().getExpenses().get(INDEX_FIRST.getZeroBased());
        DeleteExpenseFromBudgetCommand deleteExpenseFromBudgetCommand = new DeleteExpenseFromBudgetCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteExpenseFromBudgetCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");

        expectedModel.deleteExpense(expenseToDelete);
        Budget primaryBudget = expectedModel.getPrimaryBudget();
        Budget primaryBudgetCopy = primaryBudget.deepCopy();
        primaryBudgetCopy.removeExpense(expenseToDelete);
        expectedModel.setBudget(primaryBudget, primaryBudgetCopy);
        assertCommandSuccess(deleteExpenseFromBudgetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPrimaryBudget().getExpenses().size() + 1);
        DeleteExpenseFromBudgetCommand deleteExpenseFromBudgetCommand = new DeleteExpenseFromBudgetCommand(
                outOfBoundIndex);

        assertCommandFailure(deleteExpenseFromBudgetCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteExpenseFromBudgetCommand deleteFirstCommand = new DeleteExpenseFromBudgetCommand(INDEX_FIRST);
        DeleteExpenseFromBudgetCommand deleteSecondCommand = new DeleteExpenseFromBudgetCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExpenseFromBudgetCommand deleteFirstCommandCopy = new DeleteExpenseFromBudgetCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
