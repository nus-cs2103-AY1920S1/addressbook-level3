package seedu.ichifund.logic.commands.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.testutil.TypicalFundBook.getTypicalFundBook;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.ClearCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.EditTransactionDescriptorBuilder;
import seedu.ichifund.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditTransactionCommand.
 */
public class EditTransactionCommandTest {

    private Model model = new ModelManager(getTypicalFundBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditTransactionCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS,
                editedTransaction);

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);
        expectedModel.updateTransactionContext(editedTransaction);

        assertCommandSuccess(editTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction);
        Transaction editedTransaction = transactionInList.withDescription(VALID_DESCRIPTION_BUS)
                .withAmount(VALID_AMOUNT_BUS)
                .withCategory(VALID_CATEGORY_BUS)
                .build();

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BUS)
                .withAmount(VALID_AMOUNT_BUS)
                .withCategory(VALID_CATEGORY_BUS)
                .build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditTransactionCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS,
                editedTransaction);

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(INDEX_FIRST,
                new EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditTransactionCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS,
                editedTransaction);

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());

        assertCommandSuccess(editTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BUS).build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTransactionCommand standardCommand = new EditTransactionCommand(INDEX_FIRST, DESC_ALLOWANCE);

        // same values -> returns true
        EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(DESC_ALLOWANCE);
        EditTransactionCommand commandWithSameValues = new EditTransactionCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTransactionCommand(INDEX_SECOND, DESC_ALLOWANCE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTransactionCommand(INDEX_FIRST, DESC_BUS)));
    }

}
