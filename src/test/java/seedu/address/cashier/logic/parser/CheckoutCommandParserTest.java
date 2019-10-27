package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_PRICE_PAID;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_PRICE_PAID;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.NO_CASHIER;
import static seedu.address.testutil.TypicalItem.FISH_BURGER;
import static seedu.address.testutil.TypicalItem.STORYBOOK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CheckoutCommandParserTest {

    private CheckoutCommandParser parser = new CheckoutCommandParser();
    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_validAmountPresent_success() {
        model.setCashier(new PersonBuilder().build());

        // with no sales item
        assertCommandParserSuccess(parser, DESC_PRICE_PAID,
                new CheckoutCommand(0, VALID_PRICE_PAID), model, personModel);

        // with sales item added
        model.addItem(FISH_BURGER);
        model.addItem(STORYBOOK);
        double totalAmount = FISH_BURGER.getSubtotal() + STORYBOOK.getSubtotal();
        System.out.println("amt: " + totalAmount);
        System.out.println(VALID_PRICE_PAID);
        assertCommandParserSuccess(parser, DESC_PRICE_PAID,
                new CheckoutCommand(totalAmount, VALID_PRICE_PAID - totalAmount), model, personModel);
        model.clearSalesList();
    }
    /*
    @Test
    public void parse_invalidAmountPresent_failure() {
        model.clearSalesList();
        model.setCashier(new PersonBuilder().build());*/

        /*// with no sales item, negative price paid
        double totalAmount = 0;
        String message = String.format(MESSAGE_INSUFFICIENT_AMOUNT, totalAmount, totalAmount);
        assertCommandParserFailure(parser, INVALID_PRICE_PAID_1, message, model, personModel);*/

        // with sales item added, insufficient price paid
        /*model.addItem(FISH_BURGER);
        model.addItem(STORYBOOK);
        double totalAmount = FISH_BURGER.getSubtotal() + STORYBOOK.getSubtotal();
        String message = String.format(MESSAGE_INSUFFICIENT_AMOUNT, totalAmount, totalAmount);
        assertCommandParserFailure(parser, INVALID_PRICE_PAID_2, message, model, personModel);
        model.clearSalesList();
    } */

    @Test
    public void parse_cashierNotSet_failure() {
        assertCommandParserFailure(parser, DESC_PRICE_PAID, NO_CASHIER, model, personModel);
    }

    @Test
    public void parse_extraPrefix_success() {
        model.setCashier(new PersonBuilder().build());
        assertCommandParserSuccess(parser, DESC_PRICE_PAID + DESC_DESCRIPTION_FISH_BURGER,
                new CheckoutCommand(0, VALID_PRICE_PAID), model, personModel);
    }

}
