package seedu.savenus.model.wallet.exceptions;

import seedu.savenus.model.wallet.RemainingBudget;

/**
 * Signals that the budget input amount is out of bounds;
 */
public class BudgetAmountOutOfBoundsException extends RuntimeException {
    public BudgetAmountOutOfBoundsException () {
        super(RemainingBudget.BUDGET_AMOUNT_CONSTRAINTS);
    }
}
