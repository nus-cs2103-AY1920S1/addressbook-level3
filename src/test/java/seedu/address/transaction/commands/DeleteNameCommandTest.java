package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_BY_PERSON;

import java.util.function.Predicate;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;

import org.junit.jupiter.api.Test;

class DeleteNameCommandTest {
    
    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_unFilteredList_successful() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.ALICE);
        assertCommandSuccess(deleteNameCommand, model, message,
                expectedModel, personModel);
    }

    @Test
    void execute_filteredList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.ALICE);
        showNoTransaction(expectedModel);
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }

    /*@Test
    void execute_noTransactionOfPersonSpecifiedUnfilteredList_unsuccessful() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.DANIEL);
        String message = String.format(String.format(MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON,
                TypicalPersons.DANIEL.getName().toString()));
        assertCommandFailure(deleteNameCommand, model, message, personModel);
    }*/

    @Test
    void execute_noTransactionOfPersonSpecifiedFilteredListButInTransactionList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.BENSON);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.BENSON));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.BENSON);
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no transaction.
     */
    private void showNoTransaction(seedu.address.transaction.model.Model model) {
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
