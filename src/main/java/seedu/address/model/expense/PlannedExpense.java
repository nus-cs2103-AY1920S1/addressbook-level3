package seedu.address.model.expense;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of an planned expense.
 */
public class PlannedExpense extends Expense {

    /**
     * Constructs an {@code PlannedExpense}.
     */
    public PlannedExpense(Name name, Budget budget, DayNumber dayNumber) {
        super(name, budget, dayNumber);

    }
}
