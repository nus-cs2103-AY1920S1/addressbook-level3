package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.stubs.CashierModelStubAcceptingItemAdded;
import seedu.address.stubs.InventoryModelStubWithItem;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.stubs.TransactionModelStubWithTransaction;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.transaction.model.Transaction;

public class AddCommandTest {

    //private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList());
    //private CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();

    @Test
    public void constructor_nullDescription_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new AddCommand(null, 6));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws NoSuchItemException {
        Person validPerson = new PersonBuilder().build();
        PersonModelStubWithPerson modelStubWithPerson = new PersonModelStubWithPerson(validPerson);

        Transaction validTransaction = new TransactionBuilder(validPerson).build();
        TransactionModelStubWithTransaction modelStubWithTrans =
                new TransactionModelStubWithTransaction(validTransaction);

        Item validItem = new ItemBuilder().build();
        InventoryModelStubWithItem inventoryModelStubWithItem = new InventoryModelStubWithItem(validItem);

        Item anotherItem = TypicalItem.FISH_BURGER;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();

        //Model expectedModel  = new ModelManager(TypicalItem.getTypicalInventoryList());
        CommandResult commandResult = addCommand.execute(modelStubWithItem,
                modelStubWithPerson, modelStubWithTrans, inventoryModelStubWithItem);

        assertEquals(String.format(CashierMessages.MESSAGE_ADD_CASHIER, validItem),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validItem), modelStubWithItem.getItemsAdded());

    }








}
