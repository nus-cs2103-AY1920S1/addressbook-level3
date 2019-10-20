package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.*;

import seedu.address.person.model.person.Person;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubWithTransaction;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.transaction.model.Transaction;

import org.junit.jupiter.api.Test;

class BackCommandTest {

    @Test
    public void execute_successful() {
        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);
        Transaction validTrans = new TransactionBuilder(validPerson).build();
        TransactionModelStubWithTransaction modelStubWithTransaction =
                new TransactionModelStubWithTransaction(validTrans);
        BackCommand backCommand = new BackCommand();
        CommandResult commandResult = backCommand.execute(modelStubWithTransaction, modelStubWithPerson);
        assertEquals(commandResult.getFeedbackToUser(), "");
    }
}
