package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;
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
        Expense updatedExpense = new ExpenseBuilder().build();
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(updatedExpense).build();
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedExpense);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), updatedExpense);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        Expense updatedTransaction = new ExpenseBuilder().build();
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(updatedTransaction)
               .build();
        UpdateCommand updateCommand = new UpdateCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(lastTransaction, updatedTransaction);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new UpdateTransactionDescriptor());
        Transaction updatedTransaction = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedTransaction);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model.getFilteredTransactionList().get(
                TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        Transaction updatedPerson = new ExpenseBuilder(transactionInFilteredList)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA).build();
        UpdateCommand updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new UpdateTransactionDescriptorBuilder().withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                        .build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new Thrift(model.getThrift()), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
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

}
