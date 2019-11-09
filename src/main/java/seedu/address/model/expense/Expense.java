package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of expense.
 */
public abstract class Expense {

    // Compulsory fields
    private final Name name;
    private final Budget budget;

    // Optional fields
    private final DayNumber dayNumber;

    /**
     * Constructs an {@code Expense}.
     */
    public Expense(Name name, Budget budget, DayNumber dayNumber) {
        requireAllNonNull(name, budget);
        this.name = name;
        this.budget = budget;
        this.dayNumber = dayNumber;
    }

    public Name getName() {
        return name;
    }
    public Budget getBudget() {
        return budget;
    }
    public DayNumber getDayNumber() {
        return dayNumber;
    }

    /**
     * Check if two expenses are the same
     *
     * @param otherExpense The other expense to check.
     * @return Boolean of whether the expenses are the same.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        } else if (otherExpense == null) {
            return false;
        } else if (otherExpense instanceof MiscExpense && this instanceof MiscExpense) {
            return otherExpense.getName().equals(getName())
                    && otherExpense.getDayNumber().equals(getDayNumber());
        } else if (otherExpense instanceof PlannedExpense && this instanceof PlannedExpense) {
            return otherExpense.getName().equals(getName())
                    && otherExpense.getDayNumber().equals(getDayNumber());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getName().equals(getName())
                && otherExpense.getDayNumber().equals(getDayNumber())
                && otherExpense.getBudget().equals(getBudget());
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
