package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADDED_ITEM;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.CashierModelStubAcceptingItemAdded;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.util.CommandResult;

public class AddCommandTest {


    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private seedu.address.person.model.Model personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullDescription_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new AddCommand(null, 6));
    }

    @Test
    public void constructor_negativeQuantity_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new AddCommand(TypicalItem.FISH_BURGER.getDescription(), -5));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws NoSuchItemException, InsufficientAmountException {
        model.clearSalesList();
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.CHIPS);

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.CHIPS;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());
        CommandResult commandResult = addCommand.execute(modelStubWithItem, (CheckAndGetPersonByNameModel) personModel);

        assertEquals(String.format(MESSAGE_ADDED_ITEM, anotherItem.getQuantity(), anotherItem.getDescription()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(anotherItem), modelStubWithItem.getItemsAdded());
        model.clearSalesList();

    }

    @Test
    public void execute_nonExistingItemInInventory_throwsAssertionException() {
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.BLACK_SHIRT;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());

        String expectedMessage = CashierMessages.NO_SUCH_ITEM_CASHIER;
        assertCommandFailure(addCommand, modelStubWithItem, expectedMessage,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

    @Test
    public void execute_invalidQuantity_throwInsufficientStockException() {

        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.FISH_BURGER);

        Item anotherItem = TypicalItem.FISH_BURGER;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity() + 70);

        String message = String.format(MESSAGE_INSUFFICIENT_STOCK,
                anotherItem.getQuantity(), anotherItem.getDescription());
        assertCommandFailure(addCommand, model, message, (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

    @Test
    public void execute_notAvailableForSaleItem_throwsNoSuchItemForSaleException() {
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.PHONE_CASE);

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.PHONE_CASE;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());
        String expectedMessage = NO_SUCH_ITEM_FOR_SALE_CASHIER;

        assertCommandFailure(addCommand, modelStubWithItem, expectedMessage,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

}

