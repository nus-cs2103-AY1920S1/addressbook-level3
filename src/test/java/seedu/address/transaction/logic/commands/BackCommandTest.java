package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_BACKED;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.person.Person;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubWithTransaction;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;

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
        assertEquals(commandResult.getFeedbackToUser(), MESSAGE_BACKED);
    }
}
