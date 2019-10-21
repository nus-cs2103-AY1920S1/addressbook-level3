package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_TRANSACTION;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.PersonModelStubAcceptingPersonAdded;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

class DeleteIndexCommandTest {

    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());
    private PersonModelStubAcceptingPersonAdded modelStubWithPerson =
            new PersonModelStubAcceptingPersonAdded(TypicalPersons.getTypicalPersons());
    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    //this uses model stub
    void execute_validIndexUnfilteredList_successful() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        String message = String.format(MESSAGE_DELETE_TRANSACTION, TypicalTransactions.ALICE_TRANSACTION_1);
        ModelManager expectedModel = new
                ModelManager(TypicalTransactions.getTypicalTransactionList());
        System.out.println(expectedModel.getTransactionList().get(0));
        expectedModel.deleteTransaction(1);
        assertCommandSuccess(deleteIndexCommand, model, message,
                expectedModel, personModel);
    }

    @Test
    //this uses model stub
    void execute_unfilteredList_outOfBounds() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(20);
        String message = TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;
        assertCommandFailure(deleteIndexCommand, model, message, personModel);
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

        //System.out.println(expectedMessage);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    //this uses the actual model not stub with the ab also uses
    public void execute_validIndexFilteredList_outOfBounds() {
        //transaction list index is 2 but it is index 1 in filtered list (BENSON)
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(2);

        String message = TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;
        assertCommandFailure(deleteCommand, model, message, personModel);
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
