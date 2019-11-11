package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADD_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.person.Person;
import seedu.address.stubs.PersonModelStubAcceptingPersonAdded;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.util.CommandResult;

public class SetCashierCommandTest {

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    @Test
    public void constructor_nullCashier_throwsAssertionException() {
        assertThrows(AssertionError.class, () -> new SetCashierCommand(null));
    }

    @Test
    public void execute_validCashier_setSuccessful() throws NoSuchPersonException, NoSuchIndexException,
            CommandException, NoCashierFoundException,
            seedu.address.cashier.logic.commands.exception.NoSuchPersonException {

        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);

        SetCashierCommand setCashierCommand = new SetCashierCommand(validPerson);
        CommandResult commandResult = setCashierCommand.execute(model,
                modelStubWithPerson);

        String expectedMessage = String.format(MESSAGE_ADD_CASHIER, validPerson.getName());

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        expectedModel.setCashier(validPerson);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getCashier(), model.getCashier());

    }

    @Test
    public void execute_invalidCashier_failure() {
        Person nonExistingPerson = TypicalPersons.AMY;

        PersonModelStubAcceptingPersonAdded personModelStub2 =
                new PersonModelStubAcceptingPersonAdded();
        SetCashierCommand setCashierCommand = new SetCashierCommand(nonExistingPerson);

        String expectedMessage = String.format(NO_SUCH_PERSON);

        assertCommandFailure(setCashierCommand, model, expectedMessage, personModelStub2);

    }


}
