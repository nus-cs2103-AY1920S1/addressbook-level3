package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_CHECKOUT_SUCCESS;
import static seedu.address.testutil.TypicalItem.STORYBOOK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
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

    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        assertCommandFailure(checkoutCommand, model, expectedMessage, (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void execute_validTotalAmountValidChange_successful() {
        CheckoutCommand checkoutCommand = new CheckoutCommand(VALID_TOTAL_AMOUNT, VALID_CHANGE);
        Person cashier = new PersonBuilder().build();
        model.setCashier(cashier);

        model.addItem(STORYBOOK);
        String expectedMessage = String.format(MESSAGE_CHECKOUT_SUCCESS,
                Item.DECIMAL_FORMAT.format(VALID_TOTAL_AMOUNT),
                Item.DECIMAL_FORMAT.format(VALID_CHANGE));

        seedu.address.cashier.model.ModelManager expectedModel =
                new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        expectedModel.setCashier(new PersonBuilder().build());

        assertCommandSuccess(checkoutCommand, model, expectedMessage, expectedModel,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();

    }

}
