package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

public class DeleteExpenseFromBudgetCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_validIndex_success() {
        assertEquals(model.getPrimaryBudget().getDescription().fullDescription, "School related expenses");
        assertEquals(2, model.getPrimaryBudget().getExpenses().size());
        Expense expenseToDelete = model.getPrimaryBudget().getExpenses().get(INDEX_FIRST.getZeroBased());
        DeleteExpenseFromBudgetCommand command = new DeleteExpenseFromBudgetCommand(INDEX_FIRST);

        expectedModel.deleteExpense(expenseToDelete);
        Budget primaryBudget = expectedModel.getPrimaryBudget();
        Budget primaryBudgetCopy = primaryBudget.deepCopy();
        primaryBudgetCopy.removeExpense(expenseToDelete);
        expectedModel.setBudget(primaryBudget, primaryBudgetCopy);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(DeleteExpenseFromBudgetCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                expenseToDelete);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
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

        // different expense -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
