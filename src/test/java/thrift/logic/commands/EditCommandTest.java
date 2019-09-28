package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.EditCommand.EditTransactionDescriptor;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Transaction;
import thrift.testutil.EditTransactionDescriptorBuilder;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedExpense).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        Expense editedTransaction = new ExpenseBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction)
               .build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model.getFilteredTransactionList().get(
                TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        Transaction editedPerson = new ExpenseBuilder(transactionInFilteredList)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new EditTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of transactions list
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of thrift list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThrift().getTransactionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                CommandTestUtil.DESC_MEAL);

        // same values -> returns true
        EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(CommandTestUtil.DESC_MEAL);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION,
                CommandTestUtil.DESC_MEAL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                CommandTestUtil.DESC_PURCHASE)));
    }

}
