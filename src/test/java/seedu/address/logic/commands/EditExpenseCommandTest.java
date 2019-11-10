package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditExpenseCommand.
 */
public class EditExpenseCommandTest {

    private Model model = new ModelManager(getTypicalExpenseList(), new BudgetList(),
        getTypicalExchangeData(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()),
            new BudgetList(model.getBudgetList()),
            new ExchangeData(model.getExchangeData()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
        Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

        ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = expenseInList.withName(VALID_NAME_RUM).withAmount(VALID_AMOUNT_RUM)
                .withTag(VALID_TAG_ALCOHOL).build();

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM)
                .withAmount(VALID_AMOUNT_RUM).withTag(VALID_TAG_ALCOHOL).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()),
            new BudgetList(model.getBudgetList()),
            new ExchangeData(model.getExchangeData()),
            new UserPrefs());
        expectedModel.setExpense(lastExpense, editedExpense);

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST_ITEM, new EditExpenseDescriptor());
        Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()),
            new BudgetList(model.getBudgetList()), new ExchangeData(model.getExchangeData()), new UserPrefs());

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_duplicateExpenseUnfilteredList_failure() {
        Expense firstExpense = model.getFilteredExpenseList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(firstExpense).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editExpenseCommand, model, EditExpenseCommand.MESSAGE_DUPLICATE_EXPENSE, commandHistory);
    }

    @Test
    public void execute_duplicateExpenseFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_ITEM);

        // edit expense in filtered list into a duplicate in expense list
        Expense expenseInList = model.getExpenseList().getExpenseList().get(INDEX_SECOND_ITEM.getZeroBased());
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST_ITEM,
            new EditExpenseDescriptorBuilder(expenseInList).build());

        assertCommandFailure(editExpenseCommand, model, EditExpenseCommand.MESSAGE_DUPLICATE_EXPENSE, commandHistory);
    }

    @Test
    public void execute_invalidExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX,
            commandHistory);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of expense list
     */
    @Test
    public void execute_invalidExpenseIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of expense list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpenseList().getExpenseList().size());

        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex,
            new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM).build());

        assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX,
            commandHistory);
    }

    @Test
    public void equals() {
        final EditExpenseCommand standardCommand = new EditExpenseCommand(INDEX_FIRST_ITEM, DESC_VODKA);

        // same values -> returns true
        EditExpenseDescriptor copyDescriptor = new EditExpenseDescriptor(DESC_VODKA);
        EditExpenseCommand commandWithSameValues = new EditExpenseCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_SECOND_ITEM, DESC_VODKA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_FIRST_ITEM, DESC_RUM)));
    }
}
