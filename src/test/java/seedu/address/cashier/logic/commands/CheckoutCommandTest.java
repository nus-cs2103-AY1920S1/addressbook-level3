package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_CHECKOUT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.PersonBuilder;
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

    @Test
    public void execute_validTotalAmountValidChange_successful() throws Exception {
        CheckoutCommand checkoutCommand = new CheckoutCommand(VALID_TOTAL_AMOUNT, VALID_CHANGE);
        Person cashier = new PersonBuilder().build();
        model.setCashier(cashier);

        String expectedMessage = String.format(MESSAGE_CHECKOUT_SUCCESS, Item.DECIMAL_FORMAT.format(VALID_TOTAL_AMOUNT),
                Item.DECIMAL_FORMAT.format(VALID_CHANGE));

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        expectedModel.setCashier(new PersonBuilder().build());

        assertCommandSuccess(checkoutCommand, model, expectedMessage, expectedModel, personModel);

    }

}
