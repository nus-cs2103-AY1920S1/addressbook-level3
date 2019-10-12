package seedu.address.cashier.logic;

import seedu.address.cashier.commands.CheckoutCommand;
import seedu.address.cashier.logic.exception.InsufficientAmountException;
import seedu.address.cashier.logic.exception.NotANumberException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierUi;

public class CheckoutCommandParser {

    public static CheckoutCommand parse(String userInput, ModelManager modelManager) throws InsufficientAmountException,
            NotANumberException {

        double totalAmount = modelManager.getTotalAmount();
        double amount;
        double change = 0;

        try {
            amount = Double.parseDouble(userInput.substring(1));
        } catch (Exception e) {
            throw new NotANumberException(CashierUi.NOT_A_NUMBER);
        }

        if (amount == totalAmount) {
            change = 0;
        } else if (amount > totalAmount) {
            change = amount - totalAmount;
        } else {
            throw new InsufficientAmountException(CashierUi.INSUFFICIENT_AMOUNT);
        }

        CheckoutCommand checkoutCommand = new CheckoutCommand(totalAmount, change);
        return checkoutCommand;
    }
}
