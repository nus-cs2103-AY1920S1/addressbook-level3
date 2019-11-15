package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT_STRING;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DESC_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.guilttrip.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand;
import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.testutil.EditExpenseDescriptorBuilder;
import seedu.guilttrip.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 and unit tests for EditCommand.
 */
public class EditExpenseCommandTest {

    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private CommandHistory chs = new CommandHistoryStub();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editCommand = new EditExpenseCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenses().get(0), editedExpense);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, chs);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenses().size());
        //Last Expense is travel expense
        Expense lastExpense = model.getFilteredExpenses().get(indexLastExpense.getZeroBased());

        //Edit amount description and tags
        ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = expenseInList.withDesc(VALID_DESC_CLOTHING_EXPENSE)
                .withAmt(AMOUNT).withTags(VALID_TAG_FOOD).build();

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                  .withDescription(VALID_DESC_CLOTHING_EXPENSE).withAmount(AMOUNT_STRING)
                  .withTags(VALID_TAG_FOOD).build();
        EditExpenseCommand editCommand = new EditExpenseCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setExpense(lastExpense, editedExpense);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, chs);
    }

    @Test
    public void execute_sameExpenseEditedUnfilteredList_throwsIllegalValueException() {
        Expense editedExpense = model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased());

        //Edit amount description and tags
        ExpenseBuilder expenseInList = new ExpenseBuilder(editedExpense);
        //build the Same Expense
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editCommand = new EditExpenseCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_DUPLICATE_ENTRY, editedExpense);
        assertCommandFailure(editCommand, model, expectedMessage, chs);
    }

    @Test
    public void execute_filteredList_success() {
        showExpenseAtIndex(model, INDEX_THIRD_ENTRY);
        //after filtered left with 1 which is the third entry, which is why all is INDEX_FIRST_ENTRY
        Expense expenseInFilteredList = model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(expenseInFilteredList).withDesc(VALID_DESC_FOOD_EXPENSE).build();
        EditExpenseCommand editCommand = new EditExpenseCommand(INDEX_FIRST_ENTRY,
                new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE).build());

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased()), editedExpense);
        expectedModel.commitGuiltTrip();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, chs);
    }

    @Test
    public void execute_duplicateExpenseList_success() {
        //duplicate the first entry
        Expense firstExpense = model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(firstExpense).build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(firstExpense).build();
        //set the second expense as a duplicate
        EditExpenseCommand editCommand = new EditExpenseCommand(INDEX_SECOND_ENTRY, descriptor);
        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenses().get(INDEX_SECOND_ENTRY.getZeroBased()), firstExpense);
        expectedModel.commitGuiltTrip();
        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, chs);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withDescription(VALID_DESC_CLOTHING_EXPENSE).build();
        EditExpenseCommand editCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, chs);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of GuiltTrip
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_ENTRY);
        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of guilttrip book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGuiltTrip().getExpenseList().size());

        EditExpenseCommand editCommand = new EditExpenseCommand(outOfBoundIndex,
                new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_CLOTHING_EXPENSE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, chs);
    }

    @Test
    public void equals() {
        final EditExpenseCommand standardCommand = new EditExpenseCommand(INDEX_FIRST_ENTRY, DESC_FOOD_EXPENSE);

        // same values -> returns true
        EditExpenseDescriptor copyDescriptor = new EditExpenseDescriptor(DESC_FOOD_EXPENSE);
        EditExpenseCommand commandWithSameValues = new EditExpenseCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_SECOND_ENTRY, DESC_FOOD_EXPENSE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_FIRST_ENTRY, DESC_CLOTHING_EXPENSE)));
    }

}
