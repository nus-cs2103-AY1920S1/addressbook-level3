package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_TRANSACTION;

import java.util.function.Predicate;

import seedu.address.stubs.PersonModelStubAcceptingPersonAdded;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

import org.junit.jupiter.api.Test;

class DeleteIndexCommandTest {

    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());
    private PersonModelStubAcceptingPersonAdded modelStubWithPerson=
            new PersonModelStubAcceptingPersonAdded(TypicalPersons.getTypicalPersons());

    @Test
    //this uses model stub
    void execute_unfilteredList_successful() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        String message = String.format(MESSAGE_DELETE_TRANSACTION, TypicalTransactions.ALICE_TRANSACTION_1);
        TransactionModelStubAcceptingTransactionAdded expectedModel = new
                TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());
        expectedModel.deleteTransaction(1);
        assertCommandSuccess(deleteIndexCommand, modelStubWithTransaction, message,
                expectedModel, modelStubWithPerson);
    }

    @Test
    //this uses model stub
    void execute_unfilteredList_unsuccessful_outOfBounds() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(20);
        String message = TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;
        assertCommandFailure(deleteIndexCommand, modelStubWithTransaction, message, modelStubWithPerson);
    }

    @Test
    //this uses the actual model not stub with the ab also uses
    public void execute_validIndexFilteredList_success() {
        //transaction list index is 2 but it is index 1 in filtered list (BENSON)
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(1);

        String expectedMessage = String.format(MESSAGE_DELETE_TRANSACTION, TypicalTransactions.BENSON_TRANSACTION_2);

        Model expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteTransaction(2);
        showNoTransaction(expectedModel);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, modelStubWithPerson);
    }

    @Test
    //this uses the actual model not stub with the ab also uses
    public void execute_validIndexFilteredList_unsuccessful_outOfBounds() {
        //transaction list index is 2 but it is index 1 in filtered list (BENSON)
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());

        Transaction transactionToDelete = model.getFilteredList().get(2);
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(2);

        String message = TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;

        assertCommandFailure(deleteCommand, model, message, modelStubWithPerson);
    }

    /**
     * Updates {@code model}'s filtered list to show no transaction.
     */
    private void showNoTransaction(Model model) {
        Predicate<Transaction> predicate = new Predicate<Transaction>() {
            @Override
            public boolean test(Transaction transaction) {
                return false;
            }
        };
        model.updatePredicate(predicate);
        assertTrue(model.getFilteredList().gettArrList().isEmpty());
    }
}
