package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.stubs.CashierModelStubWithItem;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.util.CommandResult;

public class ClearCommandTest {

    @Test
    public void execute_successful() throws Exception {

        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);

        Item anotherItem = TypicalItem.FISH_BURGER;
        CashierModelStubWithItem cashierModelStubWithItem = new CashierModelStubWithItem(anotherItem);

        ClearCommand clearCommand = new ClearCommand();
        CommandResult commandResult = clearCommand.execute(cashierModelStubWithItem, modelStubWithPerson);

        assertEquals(commandResult.getFeedbackToUser(), CashierMessages.CLEARED_SUCCESSFULLY);
    }

}
