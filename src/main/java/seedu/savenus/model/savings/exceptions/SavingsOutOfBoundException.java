package seedu.savenus.model.savings.exceptions;

import seedu.savenus.model.util.Money;

/**
 * Signals that the user is trying to save too much money to the point it exceeds the floating limitations.
 */
public class SavingsOutOfBoundException extends RuntimeException {
    public SavingsOutOfBoundException() {
        super(String.format("You cannot add so much money to your savings account."
                        + " Limit of your Savings Account is $%.02f!",
                Money.FLOATING_POINT_RESTRICTION));
    }
}
