package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.CashierModelStubAcceptingItemAdded;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.stubs.PersonModelStubAcceptingPersonAdded;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;

public class DeleteCommandTest {

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList());

    private CashierModelStubAcceptingItemAdded cashierModelStubAcceptingItemAdded =
            new CashierModelStubAcceptingItemAdded(TypicalItem.getTypicalItems());

    private PersonModelStubAcceptingPersonAdded modelStubWithPerson=
            new PersonModelStubAcceptingPersonAdded(TypicalPersons.getTypicalPersons());

    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());

    private InventoryModelStubAcceptingItemAdded inventoryModelStubAcceptingItemAdded =
            new InventoryModelStubAcceptingItemAdded(TypicalItem.getTypicalItems());

/*    Item validItem = new ItemBuilder().build();
    InventoryModelStubWithItem inventoryModelStubWithItem = new InventoryModelStubWithItem(validItem);
*/
    @Test
    public void execute_validIndex_successful() throws NoSuchIndexException {
        DeleteCommand deleteCommand = new DeleteCommand(1);
        String message = String.format(CashierMessages.MESSAGE_DELETED_ITEM, TypicalItem.FISH_BURGER);
        System.out.println("msg1: " + message);

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList());
        expectedModel.addItem(TypicalItem.FISH_BURGER);

        System.out.println(expectedModel.getInventoryList().getItemByIndex(0));
        expectedModel.deleteItem(1);

        model.addItem(TypicalItem.FISH_BURGER);
        assertCommandSuccess(deleteCommand, model, message, expectedModel,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }

    @Test
    public void execute_outOfBoundIndex_unsuccessful() {
        DeleteCommand deleteCommand = new DeleteCommand(30);
        String message = CashierMessages.NO_SUCH_INDEX_CASHIER;
        assertCommandFailure(deleteCommand, model, message,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }

    @Test
    public void execute_negativeIndex_unsuccessful() {
        assertThrows(AssertionError.class, () -> new DeleteCommand(-5));
    }

}
