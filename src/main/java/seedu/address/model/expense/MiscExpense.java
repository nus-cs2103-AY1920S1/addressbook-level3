package seedu.address.model.expense;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of miscellaneous expense.
 */
public class MiscExpense extends Expense {

    /**
     * Constructs an {@code MiscExpense}.
     */
    public MiscExpense(Name name, Budget budget, DayNumber dayNumber) {
        super(name, budget, dayNumber);

    }
}
