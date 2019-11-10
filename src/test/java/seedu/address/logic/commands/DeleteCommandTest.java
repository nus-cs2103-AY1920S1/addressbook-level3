package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;
import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalExpenseList(), getTypicalBudgetList(), getTypicalExchangeData(),
        new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredExpenseList_success() {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getExpenseList(), getTypicalBudgetList(),
            model.getExchangeData(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    //    @Test
    //    public void execute_invalidIndexUnfilteredExpenseList_throwsCommandException() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    //    }

    @Test
    public void execute_validIndexFilteredExpenseList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_ITEM);

        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        Model expectedModel = new ModelManager(model.getExpenseList(), getTypicalBudgetList(),
            model.getExchangeData(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        showNoExpense(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    //    @Test
    //    public void execute_invalidIndexFilteredExpenseList_throwsCommandException() {
    //        showExpenseAtIndex(model, INDEX_FIRST_ITEM);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_ITEM;
    //        // ensures that outOfBoundIndex is still in bounds of expense list list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpenseList().getExpenseList().size());
    //
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    //    }
    //TODO this test has unsolved issues.

    @Test
    public void execute_validIndexUnfilteredBudgetList_success() {
        model.setViewState(ViewState.BUDGETLIST);
        Budget budgetToDelete = model.getFilteredBudgetList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete);

        ModelManager expectedModel = new ModelManager(getTypicalExpenseList(), model.getBudgetList(),
            model.getExchangeData(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        model.setViewState(ViewState.DEFAULT_EXPENSELIST);
    }

    @Test
    public void execute_invalidIndexUnfilteredBudgetList_throwsCommandException() {
        model.setViewState(ViewState.BUDGETLIST);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        model.setViewState(ViewState.DEFAULT_EXPENSELIST);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoExpense(Model model) {
        model.updateFilteredExpenseList(p -> false);

        assertTrue(model.getFilteredExpenseList().isEmpty());
    }
}
