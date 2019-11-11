package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_PRICE_PAID;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_PRICE_PAID_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_PRICE_PAID_3;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_PRICE_PAID;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.AMOUNT_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_AMOUNT;
import static seedu.address.cashier.ui.CashierMessages.NO_CASHIER;
import static seedu.address.testutil.TypicalItem.CHIPS;
import static seedu.address.testutil.TypicalItem.STORYBOOK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.inventory.util.InventoryList;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CheckoutCommandParserTest {

    private CheckoutCommandParser parser = new CheckoutCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public void setInventoryList() {
        InventoryList inventoryList = new InventoryList();
        inventoryList.add(CHIPS);
        model = new seedu.address.cashier.model.ModelManager(new seedu.address.cashier.util.InventoryList(
                inventoryList.getInventoryListInArrayList()),
                TypicalTransactions.getTypicalTransactionList());
    }

    @Test
    public void parse_validAmountPresent_success() {
        model.setCashier(new PersonBuilder().build());

        // with no sales item
        assertCommandParserSuccess(parser, DESC_PRICE_PAID,
                new CheckoutCommand(0, VALID_PRICE_PAID), model,
                (CheckAndGetPersonByNameModel) personModel);

        model.clearSalesList();
    }

    @Test
    public void parse_invalidNotANumberAmountPresent_failure() {
        model.clearSalesList();
        model.setCashier(new PersonBuilder().build());
        model.addItem(STORYBOOK);
        assertCommandParserFailure(parser, INVALID_PRICE_PAID_3, AMOUNT_NOT_A_NUMBER, model,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

    @Test
    public void parse_invalidAmountPresent_failure() {
        setInventoryList();
        model.clearSalesList();
        model.setCashier(new PersonBuilder().build());
        model.addItem(CHIPS);
        double totalAmount = CHIPS.getSubtotal();
        String message = String.format(MESSAGE_INSUFFICIENT_AMOUNT, totalAmount, totalAmount);
        assertCommandParserFailure(parser, INVALID_PRICE_PAID_2, message, model,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

    @Test
    public void parse_cashierNotSet_failure() {
        assertCommandParserFailure(parser, DESC_PRICE_PAID, NO_CASHIER, model,
                (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_extraPrefix_success() {
        model.setCashier(new PersonBuilder().build());
        assertCommandParserSuccess(parser, DESC_PRICE_PAID + DESC_DESCRIPTION_FISH_BURGER,
                new CheckoutCommand(0, VALID_PRICE_PAID), model,
                (CheckAndGetPersonByNameModel) personModel);
    }

}
