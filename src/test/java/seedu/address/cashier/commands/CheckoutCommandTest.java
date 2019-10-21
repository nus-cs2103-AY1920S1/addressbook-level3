package seedu.address.cashier.commands;

import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.stubs.PersonModelStub;
import seedu.address.stubs.TransactionModelStubAcceptingTransactionAdded;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CheckoutCommandTest {

    private Double validTotalAmount = 90.43;
    private Double validChange = 4.21;
    private Double invalidTotalAmount = -50.32;
    private Double invalidChange = -3.21;

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList());

    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model personModelStub = new PersonModelStub();

    private TransactionModelStubAcceptingTransactionAdded modelStubWithTransaction =
            new TransactionModelStubAcceptingTransactionAdded(TypicalTransactions.getTypicalTransactions());

    private InventoryModelStubAcceptingItemAdded inventoryModelStubAcceptingItemAdded =
            new InventoryModelStubAcceptingItemAdded(TypicalItem.getTypicalItems());

    @Test
    public void execute_validTotalAmountAndChange_withoutCashier_throwsNoCashierFoundException() {
        CheckoutCommand checkoutCommand = new CheckoutCommand(validTotalAmount, validChange);
        String expectedMessage = CashierMessages.NO_CASHIER;
        assertCommandFailure(checkoutCommand, model, expectedMessage,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
    }
/*
    @Test
    public void execute_validTotalAmount_validChange_successful() throws Exception {
        CheckoutCommand checkoutCommand = new CheckoutCommand(validTotalAmount, validChange);
        Person cashier = new PersonBuilder().build();
        model.setCashier(cashier);

        CommandResult commandResult = checkoutCommand.execute(model,
                personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
        String expectedMessage = String.format(MESSAGE_CHECKOUT_SUCCESS, Item.DECIMAL_FORMAT.format(validTotalAmount),
                Item.DECIMAL_FORMAT.format(validChange));

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList());
        expectedModel.setCashier(new PersonBuilder().build());

        System.out.println("expected msg: " + expectedMessage);
        //System.out.println("msg: " + commandResult.getFeedbackToUser());
        //assertCommandSuccess(checkoutCommand, model, expectedMessage, expectedModel,
                //personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);
        assertCommandSuccess(checkoutCommand, model, expectedMessage,
                expectedModel, personModel, modelStubWithTransaction, inventoryModelStubAcceptingItemAdded);

        //assertEquals(expectedMessage, commandResult.getFeedbackToUser());

    }
*/
}
