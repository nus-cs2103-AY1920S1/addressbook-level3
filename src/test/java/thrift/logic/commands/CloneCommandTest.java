package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandFailure;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static thrift.model.transaction.TransactionDate.DATE_FORMATTER;
import static thrift.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

public class CloneCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
            new PastUndoableCommands());

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CloneCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transactionToClone = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_SECOND_TRANSACTION.getZeroBased());
        CloneCommand cloneCommand = new CloneCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        Income expectedTransaction = new IncomeBuilder()
                .withDescription(transactionToClone.getDescription().value)
                .withDate(DATE_FORMATTER.format(new Date()))
                .withValue(transactionToClone.getValue().getUnformattedString())
                .withRemark(transactionToClone.getRemark().value)
                .withTags(transactionToClone.getTags().iterator().next().tagName)
                .build();
        String expectedMessage = String.format(CloneCommand.MESSAGE_CLONE_TRANSACTION_SUCCESS, expectedTransaction);

        ModelManager expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.addIncome(expectedTransaction);

        assertCommandSuccess(cloneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        CloneCommand cloneCommand = new CloneCommand(outOfBoundIndex);

        assertCommandFailure(cloneCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Transaction transactionToClone = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        CloneCommand cloneCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Expense expectedTransaction = new ExpenseBuilder()
                .withDescription(transactionToClone.getDescription().value)
                .withDate(DATE_FORMATTER.format(new Date()))
                .withValue(transactionToClone.getValue().getUnformattedString())
                .withRemark(transactionToClone.getRemark().value)
                .withTags(transactionToClone.getTags().iterator().next().tagName)
                .build();
        String expectedMessage = String.format(CloneCommand.MESSAGE_CLONE_TRANSACTION_SUCCESS, expectedTransaction);

        ModelManager expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.addExpense(expectedTransaction);

        assertCommandSuccess(cloneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of thrift list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThrift().getTransactionList().size());

        CloneCommand cloneCommand = new CloneCommand(outOfBoundIndex);

        assertCommandFailure(cloneCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void undo_undoCloneCommand_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Transaction transactionToClone = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        Expense expectedTransaction = new ExpenseBuilder()
                .withDescription(transactionToClone.getDescription().value)
                .withDate(DATE_FORMATTER.format(new Date()))
                .withValue(transactionToClone.getValue().getUnformattedString())
                .withRemark(transactionToClone.getRemark().value)
                .withTags(transactionToClone.getTags().stream().map(tag -> tag.tagName).toArray(String[]::new))
                .build();

        //simulates user performing clone command
        expectedModel.addExpense(expectedTransaction);
        String expectedMessage = String.format(CloneCommand.MESSAGE_CLONE_TRANSACTION_SUCCESS, expectedTransaction);
        CloneCommand cloneCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(cloneCommand, model, expectedMessage, expectedModel);

        //undo
        expectedModel.deleteLastTransaction();
        assertUndoCommandSuccess(cloneCommand, model, expectedModel);
    }

    @Test
    public void redo_redoCloneCommand_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Transaction transactionToClone = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        Expense expectedTransaction = new ExpenseBuilder()
                .withDescription(transactionToClone.getDescription().value)
                .withDate(DATE_FORMATTER.format(new Date()))
                .withValue(transactionToClone.getValue().getUnformattedString())
                .withRemark(transactionToClone.getRemark().value)
                .withTags(transactionToClone.getTags().stream().map(tag -> tag.tagName).toArray(String[]::new))
                .build();

        //simulates user performing clone command
        expectedModel.addExpense(expectedTransaction);
        String expectedMessage = String.format(CloneCommand.MESSAGE_CLONE_TRANSACTION_SUCCESS, expectedTransaction);
        CloneCommand cloneCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(cloneCommand, model, expectedMessage, expectedModel);

        //undo
        expectedModel.deleteLastTransaction();
        assertUndoCommandSuccess(cloneCommand, model, expectedModel);

        //redo
        expectedModel.addExpense(expectedTransaction);
        assertRedoCommandSuccess(cloneCommand, model, expectedModel);
    }

    @Test
    public void equals() {
        CloneCommand cloneFirstCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        CloneCommand cloneFirstCommandDuplicate = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        CloneCommand cloneSecondCommand = new CloneCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(cloneFirstCommand.equals(cloneFirstCommand));

        // same values for different objects -> returns true
        assertTrue(cloneFirstCommand.equals(cloneFirstCommandDuplicate));

        // different types -> returns false
        assertFalse(cloneFirstCommand.equals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION)));

        // comparing with null -> returns false
        assertFalse(cloneFirstCommand.equals(null));

        // different values for different objects -> returns false
        assertFalse(cloneFirstCommand.equals(cloneSecondCommand));
    }
}
