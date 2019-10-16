package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import seedu.address.person.model.person.Person;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

import org.junit.jupiter.api.Test;

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
        modelStubWithTrans.addTransaction(validTransaction);
        CommandResult commandResult = new AddCommand(validTransaction).execute(modelStubWithTrans, modelStubWithPerson);
        assertEquals(String.format(TransactionMessages.MESSAGE_ADD_TRANSACTION, validTransaction),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStubWithTrans.getTransactionsAdded());
    }

    //To be tested when duplicate check is in place.
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        assertEquals(1,1);
    }

}
