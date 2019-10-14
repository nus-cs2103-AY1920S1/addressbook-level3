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
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExpenseList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXPENSE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
        Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

        ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = expenseInList.withName(VALID_NAME_RUM).withAmount(VALID_AMOUNT_RUM)
                .withTags(VALID_TAG_ALCOHOL).build();

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM)
                .withAmount(VALID_AMOUNT_RUM).withTags(VALID_TAG_ALCOHOL).build();
        EditCommand editCommand = new EditCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()), new UserPrefs());
        expectedModel.setExpense(lastExpense, editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXPENSE, new EditExpenseDescriptor());
        Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        Expense expenseInFilteredList = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(expenseInFilteredList).withName(VALID_NAME_RUM).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXPENSE,
                new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new ExpenseList(model.getExpenseList()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateExpenseUnfilteredList_failure() {
        Expense firstExpense = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(firstExpense).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_EXPENSE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXPENSE);
    }

    @Test
    public void execute_duplicateExpenseFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        // edit expense in filtered list into a duplicate in expense list
        Expense expenseInList = model.getExpenseList().getExpenseList().get(INDEX_SECOND_EXPENSE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXPENSE,
                new EditExpenseDescriptorBuilder(expenseInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXPENSE);
    }

    @Test
    public void execute_invalidExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of expense list
     */
    @Test
    public void execute_invalidExpenseIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        Index outOfBoundIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of expense list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpenseList().getExpenseList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_EXPENSE, DESC_VODKA);

        // same values -> returns true
        EditExpenseDescriptor copyDescriptor = new EditExpenseDescriptor(DESC_VODKA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_EXPENSE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_EXPENSE, DESC_VODKA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_EXPENSE, DESC_RUM)));
    }

}
