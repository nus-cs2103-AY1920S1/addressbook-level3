package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for UpdateCommand
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
            new PastUndoableCommands());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // Create initial Expense from first element in TypicalTransactions' list, to reflect original transaction after
        // updating (UpdateCommandTest private class attribute) model's first item.
        Income updatedExpense = new IncomeBuilder(model.getFilteredTransactionList().get(1))
                // .withTags(model.getFilteredTransactionList().get(0).getTags().iterator().next().tagName)
                .build();
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, updatedExpense);
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(updatedExpense).build();
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION, descriptor);

        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedExpense);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(1), updatedExpense);

        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal,
                expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        Expense updatedTransaction = new ExpenseBuilder(lastTransaction).build();
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, updatedTransaction);
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(updatedTransaction)
                .build();
        UpdateCommand updateCommand = new UpdateCommand(indexLastTransaction, descriptor);

        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS,
                updatedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(lastTransaction, updatedTransaction);

        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new UpdateTransactionDescriptor());
        Transaction updatedTransaction = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS,
                updatedTransaction);
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, updatedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());

        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model.getFilteredTransactionList().get(
                TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        Transaction updatedPerson = new ExpenseBuilder(transactionInFilteredList)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA).build();
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, updatedPerson);
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new UpdateTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());

        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA).build();
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(updateCommand, model,
                Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of transactions list
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of thrift list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThrift().getTransactionList().size());

        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex,
                new UpdateTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());

        CommandTestUtil.assertCommandFailure(updateCommand, model,
                Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void undo_undoUpdateExpense_success() throws CommandException {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, lastTransaction);

        UpdateTransactionDescriptor updateTransactionDescriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription("Chicken")
                .build();
        Transaction updatedTransaction = new Expense(updateTransactionDescriptor.getDescription().get(),
                lastTransaction.getValue(), lastTransaction.getRemark(), lastTransaction.getDate(),
                lastTransaction.getTags());
        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS,
                updatedTransaction);
        UpdateCommand updateCommand = new UpdateCommand(indexLastTransaction, updateTransactionDescriptor);

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(lastTransaction, updatedTransaction);

        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal, expectedModel);
        expectedModel.setTransaction(updatedTransaction, lastTransaction);
        assertUndoCommandSuccess(updateCommand, model, expectedModel);
    }

    @Test
    public void redo_redoUpdateExpense_success() throws CommandException {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());
        String expectedMessageOriginal = String.format(UpdateCommand.MESSAGE_ORIGINAL_TRANSACTION, lastTransaction);

        UpdateTransactionDescriptor updateTransactionDescriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription("Chicken")
                .build();
        Transaction updatedTransaction = new Expense(updateTransactionDescriptor.getDescription().get(),
                lastTransaction.getValue(), lastTransaction.getRemark(), lastTransaction.getDate(),
                lastTransaction.getTags());
        String expectedMessageUpdated = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS,
                updatedTransaction);
        UpdateCommand updateCommand = new UpdateCommand(indexLastTransaction, updateTransactionDescriptor);

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(lastTransaction, updatedTransaction);
        assertCommandSuccess(updateCommand, model, expectedMessageUpdated + expectedMessageOriginal, expectedModel);

        //test undo
        expectedModel.setTransaction(updatedTransaction, lastTransaction);
        assertUndoCommandSuccess(updateCommand, model, expectedModel);

        //test redo
        expectedModel.setTransaction(lastTransaction, updatedTransaction);
        assertRedoCommandSuccess(updateCommand, model, expectedModel);
    }


    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                CommandTestUtil.DESC_MEAL);

        // same values -> returns true
        UpdateTransactionDescriptor copyDescriptor = new UpdateTransactionDescriptor(CommandTestUtil.DESC_MEAL);
        UpdateCommand commandWithSameValues = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION,
                CommandTestUtil.DESC_MEAL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                CommandTestUtil.DESC_PURCHASE)));
    }

    @Test
    public void execute_wrongExecuteCalled_throwsCommandException() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        Index firstIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        UpdateCommand updateCommand = new UpdateCommand(firstIndex,
                new UpdateTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());
        assertThrows(CommandException.class, () -> updateCommand.execute(model));
    }
}
