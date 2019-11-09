package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.ui.CashierMessages.AMOUNT_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_AMOUNT;
import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.logic.commands.exception.NotANumberException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;

/**
 * Parses input arguments and creates a new CheckoutCommand object.
 */
public class CheckoutCommandParser implements Parser {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the CheckoutCommand
     * and returns an CheckoutCommand object for execution.
     * @throws InsufficientAmountException if the input is less than the total amount
     * @throws NotANumberException if the input is not a number
     */
    public CheckoutCommand parse(String userInput, Model modelManager,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws InsufficientAmountException,
            NotANumberException, NoCashierFoundException, AmountExceededException {

        double totalAmount = modelManager.getTotalAmount();
        double amount;
        double change = 0;

        try {
            modelManager.getCashier();
        } catch (NoCashierFoundException e) {
            logger.info("Cashier is not found");
            throw new NoCashierFoundException(CashierMessages.NO_CASHIER);
        }

        try {
            String[] input = userInput.split(" ");
            amount = Double.parseDouble(input[1]);
        } catch (Exception e) {
            throw new NotANumberException(AMOUNT_NOT_A_NUMBER);
        }

        if (amount == totalAmount) {
            change = 0;
        } else if (amount > totalAmount) {
            change = amount - totalAmount;
        } else {
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_AMOUNT,
                    DECIMAL_FORMAT.format(totalAmount), DECIMAL_FORMAT.format(totalAmount)));
        }

        CheckoutCommand checkoutCommand = new CheckoutCommand(totalAmount, change);
        return checkoutCommand;
    }
}
