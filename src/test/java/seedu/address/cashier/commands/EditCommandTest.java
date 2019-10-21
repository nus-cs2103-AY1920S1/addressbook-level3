package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class EditCommandTest {

    private static final int VALID_INDEX = 2;
    private static final int VALID_QUANTITY = 59;
    private static final int INVALID_INDEX_1 = 0;
    private static final int INVALID_INDEX_2 = 100;
    private static final int INVALID_QUANTITY = -5;
    private static final int INVALID_QUANTITY_STORYBOOK = 40;

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList());

    private Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());

    private InventoryModelStubAcceptingItemAdded inventoryModelStubAcceptingItemAdded =
            new InventoryModelStubAcceptingItemAdded(TypicalItem.getTypicalItems());

    @Test
    public void execute_validIndex_validQuantity_successful() throws NoSuchIndexException {
        EditCommand editCommand = new EditCommand(VALID_INDEX, VALID_QUANTITY);
        String message = String.format(CashierMessages.MESSAGE_EDIT_SUCCESS,
                TypicalItem.STORYBOOK.getDescription(), VALID_QUANTITY);

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList());
        expectedModel.addItem(TypicalItem.FISH_BURGER);
        expectedModel.addItem(TypicalItem.STORYBOOK);
        expectedModel.editItem(VALID_INDEX, VALID_QUANTITY);

        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        assertCommandSuccess(editCommand, model, message, expectedModel,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }

    @Test
    public void execute_invalidIndex_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new EditCommand(INVALID_INDEX_1, VALID_QUANTITY));
    }

    @Test
    public void execute_invalidIndex_failure() {
        EditCommand editCommand = new EditCommand(INVALID_INDEX_2, VALID_QUANTITY);
        String message = CashierMessages.NO_SUCH_INDEX_CASHIER;
        assertCommandFailure(editCommand, model, message,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }

    @Test
    public void execute_invalidQuantity_failure() {
        EditCommand editCommand = new EditCommand(VALID_INDEX, INVALID_QUANTITY_STORYBOOK);
        String message = String.format(CashierMessages.MESSAGE_INSUFFICIENT_STOCK,
                TypicalItem.STORYBOOK.getQuantity(), TypicalItem.STORYBOOK.getDescription());

        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        assertCommandFailure(editCommand, model, message,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }

    @Test
    public void execute_invalidQuantity_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new EditCommand(VALID_INDEX, INVALID_QUANTITY));
    }

}

