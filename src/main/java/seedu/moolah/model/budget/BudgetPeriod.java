package seedu.moolah.model.budget;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Period;

/**
 * Represents fixed budgeting periods.
 */
public enum BudgetPeriod {
    DAY("day", Period.ofDays(1)),
    WEEK("week", Period.ofWeeks(1)),
    MONTH("month", Period.ofMonths(1)),
    YEAR("year", Period.ofYears(1)),
    INFINITY("infinity", Period.ofYears(100));

    private final String label;
    private final Period period;

    /**
     * Constructs a BudgetPeriod object with a string description and a period.
     *
     * @param label A string description of this BudgetPeriod.
     * @param period A Period representing the length of this BudgetPeriod.
     */
    BudgetPeriod(String label, Period period) {
        requireAllNonNull(label, period);
        this.label = label;
        this.period = period;
    }

    public Period getPeriod() {
        return this.period;
    }

    /**
     * Generates a string representation of this BudgetPeriod.
     *
     * @return A string that describes the period.
     */
    @Override
    public String toString() {
        return this.label;
    }
}
