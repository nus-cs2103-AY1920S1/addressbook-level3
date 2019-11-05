package seedu.savenus.model.wallet.exceptions;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.wallet.DaysToExpire;

/**
 * Signals that the budget input amount is out of bounds;
 */
public class BudgetDurationOutOfBoundsException extends IllegalValueException {
    public BudgetDurationOutOfBoundsException() {
        super(DaysToExpire.INTEGER_CONSTRAINTS);
    }
}
