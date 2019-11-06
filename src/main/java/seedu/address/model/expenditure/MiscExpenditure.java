package seedu.address.model.expenditure;

import java.util.Optional;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of expenditure.
 */
public class MiscExpenditure extends Expenditure {

    /**
     * Constructs an {@code MiscExpenditure}.
     */
    public MiscExpenditure(Name name, Budget budget, DayNumber dayNumber) {
        super(name, budget, dayNumber);

    }

    /**
     * Constructs an misc expenditure with optional dayNumber field.
     */
    public MiscExpenditure(Name name, Budget budget, Optional<DayNumber> dayNumber) {
        super(name, budget, dayNumber);

    }
}
