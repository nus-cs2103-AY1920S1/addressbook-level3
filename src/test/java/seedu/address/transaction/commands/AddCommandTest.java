package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() {
        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);
        Transaction validTransaction = new TransactionBuilder(validPerson).build();
        TransactionModelStubAcceptingTransactionAdded modelStubWithTrans =
                new TransactionModelStubAcceptingTransactionAdded();
        //modelStubWithTrans.addTransaction(validTransaction);
        CommandResult commandResult = new AddCommand(validTransaction).execute(modelStubWithTrans, modelStubWithPerson);
        assertEquals(String.format(TransactionMessages.MESSAGE_ADD_TRANSACTION, validTransaction),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStubWithTrans.getTransactionsAdded());
    }

    @Test
    public void execute_personAcceptedByModelFilteredList_addSuccessful() {
        Model model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        Transaction transaction = new TransactionBuilder(TypicalPersons.AMY).build();
        AddCommand addCommand = new AddCommand(transaction);
        seedu.address.person.model.Model personModel =
                new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.ALICE.getName().toString());
        expectedModel.resetPredicate();
        expectedModel.addTransaction(transaction);
        String message = String.format(TransactionMessages.MESSAGE_ADD_TRANSACTION, transaction);
        assertCommandSuccess(addCommand, model, message, expectedModel, personModel);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Transaction transactionAlice = new TransactionBuilder(alice).build();
        Transaction transactionBob = new TransactionBuilder(bob).build();
        AddCommand addAliceCommand = new AddCommand(transactionAlice);
        AddCommand addBobCommand = new AddCommand(transactionBob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(transactionAlice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
