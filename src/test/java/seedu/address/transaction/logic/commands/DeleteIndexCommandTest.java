package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.logic.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_NEGATIVE_TRANSACTION;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_POSITIVE_TRANSACTION;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

class DeleteIndexCommandTest {

    private seedu.address.transaction.model.ModelManager model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private CheckAndGetPersonByNameModel personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteIndexCommand(1)
                .execute(null, null));
    }

    @Test
    //this uses model stub
    void execute_validIndexUnfilteredList_successful() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        String message = String.format(MESSAGE_DELETE_POSITIVE_TRANSACTION, TypicalTransactions.ALICE_TRANSACTION_1);
        seedu.address.transaction.model.ModelManager expectedModel = new
                seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        System.out.println(expectedModel.getTransactionList().get(0));
        expectedModel.deleteTransaction(1);
        assertCommandSuccess(deleteIndexCommand, model, message,
                expectedModel, personModel);
    }

    @Test
    void execute_validIndexUnfilteredListNegativeTransaction_successful() {
        seedu.address.transaction.model.ModelManager model =
                new seedu.address.transaction.model.ModelManager(
                        new TypicalTransactions().getTransactionListWithReimbursementNeeded());
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        String message = String.format(MESSAGE_DELETE_NEGATIVE_TRANSACTION,
                new TypicalTransactions().getTransactionsWithReimbursements().get(0));
        seedu.address.transaction.model.ModelManager expectedModel = new
                seedu.address.transaction.model.ModelManager(
                        new TypicalTransactions().getTransactionListWithReimbursementNeeded());
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

        String expectedMessage = String.format(MESSAGE_DELETE_POSITIVE_TRANSACTION,
                TypicalTransactions.BENSON_TRANSACTION_2);

        Model expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
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
        assertTrue(model.getFilteredList().getTarrList().isEmpty());
    }

    @Test
    public void equals() {

        DeleteIndexCommand delete1Command = new DeleteIndexCommand(1);
        DeleteIndexCommand anotherDelete1Command = new DeleteIndexCommand(1);
        DeleteIndexCommand delete2Command = new DeleteIndexCommand(2);

        // same object -> returns true
        assertTrue(delete1Command.equals(delete1Command));

        assertTrue(delete1Command.equals(anotherDelete1Command));

        // different types -> returns false
        assertFalse(delete1Command.equals(1));

        // null -> returns false
        assertFalse(delete1Command.equals(null));

        // different delete index command -> returns false
        assertFalse(delete1Command.equals(delete2Command));
    }
}
