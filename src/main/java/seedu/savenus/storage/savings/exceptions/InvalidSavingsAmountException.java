package seedu.savenus.storage.savings.exceptions;

import seedu.savenus.model.savings.Savings;

//@@author fatclarence
/**
 * Signals that the savings input amount is invalid, which 0 or less.;
 */
public class InvalidSavingsAmountException extends RuntimeException {
    public InvalidSavingsAmountException() {
        super(Savings.MESSAGE_CONSTRAINTS);
    }
}
