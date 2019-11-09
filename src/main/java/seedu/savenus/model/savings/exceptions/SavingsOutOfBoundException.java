package seedu.savenus.model.savings.exceptions;

/**
 * Signals that the user is trying to save too much money to the point it exceeds the floating limitations.
 */
public class SavingsOutOfBoundException extends RuntimeException {
    public SavingsOutOfBoundException() {
        super("You cannot add so much money to your savings account. Limit of your Savings Account is $1,000,000!");
    }
}
