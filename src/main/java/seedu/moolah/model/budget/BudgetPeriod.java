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

    BudgetPeriod(String label, Period period) {
        requireAllNonNull(label, period);
        this.label = label;
        this.period = period;
    }

    public Period getPeriod() {
        return this.period;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
