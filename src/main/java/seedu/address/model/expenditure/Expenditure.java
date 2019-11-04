package seedu.address.model.expenditure;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

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


    /**
     * Check if two expenditures are the same
     *
     * @param otherExpenditure The other expenditure to check.
     * @return Boolean of whether the expenditures are the same.
     */
    public boolean isSameExpenditure(Expenditure otherExpenditure) {
        if (otherExpenditure == this) {
            return true;
        } else if (otherExpenditure == null) {
            return false;
        } else if (otherExpenditure instanceof Expenditure) {
            return otherExpenditure.getName().equals(getName())
                    && otherExpenditure.getDayNumber().get().equals(getDayNumber().get());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expenditure)) {
            return false;
        }

        Expenditure otherExpenditure = (Expenditure) other;
        return otherExpenditure.getName().equals(getName())
                && otherExpenditure.getDayNumber().equals(getDayNumber())
                && otherExpenditure.getBudget().equals(getBudget());
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
