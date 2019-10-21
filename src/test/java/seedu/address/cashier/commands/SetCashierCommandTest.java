package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADD_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.stubs.PersonModelStub;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.exception.NoSuchPersonException;

public class SetCashierCommandTest {

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList());

    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model personModelStub = new PersonModelStub();

    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());

    private InventoryModelStubAcceptingItemAdded inventoryModelStubAcceptingItemAdded =
            new InventoryModelStubAcceptingItemAdded(TypicalItem.getTypicalItems());

    @Test
    public void execute_nullCashier_throwsAssertionException() {
        assertThrows(AssertionError.class, () -> new SetCashierCommand(null));
    }

    @Test
    public void execute_validCashier_setSuccessful() throws NoSuchPersonException, NoSuchIndexException,
            CommandException, NoCashierFoundException {

        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);

        SetCashierCommand setCashierCommand = new SetCashierCommand(validPerson);
        CommandResult commandResult = setCashierCommand.execute(model,
                modelStubWithPerson, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);

        String expectedMessage = String.format(MESSAGE_ADD_CASHIER, validPerson.getName());

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList());
        expectedModel.setCashier(validPerson);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getCashier(), model.getCashier());

    }

    @Test
    public void execute_invalidCashier_failure() throws NoSuchPersonException, NoSuchIndexException, CommandException {
        Person nonExistingPerson = TypicalPersons.IDA;

        SetCashierCommand setCashierCommand = new SetCashierCommand(nonExistingPerson);
        CommandResult commandResult = setCashierCommand.execute(model,
                personModelStub, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);

        System.out.println(commandResult.getFeedbackToUser());
        String expectedMessage = String.format(NO_SUCH_PERSON);

        assertTrue(expectedMessage.equals(commandResult.getFeedbackToUser()));

    }








}
