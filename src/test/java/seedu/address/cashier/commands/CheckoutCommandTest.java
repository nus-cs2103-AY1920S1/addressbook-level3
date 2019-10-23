package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.PersonModelStub;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CheckoutCommandTest {

    private static final Double VALID_TOTAL_AMOUNT = 90.43;
    private static final Double VALID_CHANGE = 4.21;
    private static final Double INVALID_TOTAL_AMOUNT = -50.32;
    private static final Double INVALID_CHANGE = -3.21;

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model personModelStub = new PersonModelStub();

    @Test
    public void constructor_invalidTotalAmount_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new CheckoutCommand(INVALID_TOTAL_AMOUNT, VALID_CHANGE));
    }

    @Test
    public void constructor_invalidChange_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new CheckoutCommand(VALID_TOTAL_AMOUNT, INVALID_CHANGE));
    }

    @Test
    public void execute_validTotalAmountAndChangeWithoutCashier_throwsNoCashierFoundException() {
        CheckoutCommand checkoutCommand = new CheckoutCommand(VALID_TOTAL_AMOUNT, VALID_CHANGE);
        String expectedMessage = CashierMessages.NO_CASHIER;
        assertCommandFailure(checkoutCommand, model, expectedMessage, personModel);
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

    }*/

}
