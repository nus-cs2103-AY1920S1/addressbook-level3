package seedu.address.cashier.logic;

import static seedu.address.cashier.ui.CashierMessages.AMOUNT_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_AMOUNT;

import seedu.address.cashier.commands.CheckoutCommand;
import seedu.address.cashier.logic.exception.InsufficientAmountException;
import seedu.address.cashier.logic.exception.NotANumberException;
import seedu.address.cashier.model.Model;

/**
 * Parses input arguments and creates a new CheckoutCommand object.
 */
public class CheckoutCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckoutCommand
     * and returns an CheckoutCommand object for execution.
     * @throws InsufficientAmountException if the input is less than the total amount
     * @throws NotANumberException if the input is not a number
     */
    public static CheckoutCommand parse(String userInput, Model modelManager) throws InsufficientAmountException,
            NotANumberException {

        double totalAmount = modelManager.getTotalAmount();
        double amount;
        double change = 0;

        try {
            amount = Double.parseDouble(userInput.substring(1));
        } catch (Exception e) {
            throw new NotANumberException(AMOUNT_NOT_A_NUMBER);
        }

        if (amount == totalAmount) {
            change = 0;
        } else if (amount > totalAmount) {
            change = amount - totalAmount;
        } else {
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_AMOUNT, totalAmount, totalAmount));
        }

        CheckoutCommand checkoutCommand = new CheckoutCommand(totalAmount, change);
        return checkoutCommand;
    }
}
