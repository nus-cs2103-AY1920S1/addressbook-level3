package seedu.moolah.logic.commands.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.EditExpenseDescriptorBuilder;
import seedu.moolah.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditExpenseCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand command = new EditExpenseCommand(INDEX_FIRST, descriptor);

        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
        Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

        ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = expenseInList
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI)
                .withPrice(VALID_EXPENSE_PRICE_TAXI)
                .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI)
                .withPrice(VALID_EXPENSE_PRICE_TAXI)
                .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        EditExpenseCommand command = new EditExpenseCommand(indexLastExpense, descriptor);

        expectedModel.setExpense(lastExpense, editedExpense);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_noFieldSpecifiedUnfilteredList_success() {
        EditExpenseCommand command = new EditExpenseCommand(INDEX_FIRST, new EditExpenseDescriptor());
        Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST.getZeroBased());

        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()));

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_filteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST);

        Expense expenseInFilteredList = model
                .getFilteredExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(expenseInFilteredList)
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
        EditExpenseCommand command = new EditExpenseCommand(INDEX_FIRST, new EditExpenseDescriptorBuilder()
                        .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build());

        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription())
                .setMooLah(model.getMooLah()).setExpensePredicate(model.getFilteredExpensePredicate()));

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void run_invalidExpenseIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMooLah().getExpenseList().size());

        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex,
                new EditExpenseDescriptorBuilder().withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build());

        assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditExpenseCommand standardCommand = new EditExpenseCommand(INDEX_FIRST, DESC_CHICKEN);

        // same values -> returns true
        EditExpenseDescriptor copyDescriptor = new EditExpenseDescriptor(DESC_CHICKEN);
        EditExpenseCommand commandWithSameValues = new EditExpenseCommand(INDEX_FIRST, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditExpenseCommand(INDEX_SECOND, DESC_CHICKEN));
    }

}
