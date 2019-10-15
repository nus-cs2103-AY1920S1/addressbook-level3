package seedu.address.model.expenditure;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

import java.util.Optional;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Generic abstraction of expenditure.
 */
public class Expenditure {

    // Compulsory fields
    private final Name name;
    private final Budget budget;

    // Optional fields
    private final DayNumber dayNumber;

    // Indicate whether the expenditure can be deleted manually
    private final boolean isRemovable;

    /**
     * Constructs an {@code Expenditure}.
     */
    public Expenditure(Name name, Budget budget, DayNumber dayNumber, boolean isRemovable) {
        this.isRemovable = isRemovable;
        requireAllNonNull(name, budget, dayNumber);
        this.name = name;
        this.budget = budget;
        this.dayNumber = dayNumber;
    }

    /**
     * Constructs an expenditure with optional dayNumber field.
     */
    public Expenditure(Name name, Budget budget, Optional<DayNumber> dayNumber, boolean isRemovable) {
        this.isRemovable = isRemovable;
        requireAllNonNull(name, budget, dayNumber);
        this.name = name;
        this.budget = budget;
        if (dayNumber.isPresent()) {
            this.dayNumber = dayNumber.get();
        } else {
            this.dayNumber = null;
        }
    }


    public Name getName() {
        return name;
    }
    public Budget getBudget() {
        return budget;
    }
    public Optional<DayNumber> getDayNumber() {
        return Optional.ofNullable(dayNumber);
    }
    public boolean getRemovability() {
        return isRemovable;
    }

}
