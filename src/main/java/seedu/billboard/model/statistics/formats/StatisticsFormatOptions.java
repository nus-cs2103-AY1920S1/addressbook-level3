package seedu.billboard.model.statistics.formats;

import java.util.Optional;

import seedu.billboard.commons.core.date.DateInterval;

/**
 * Encapsulates the changes in options that a user can select with regards to the display of the statistics.
 */
public class StatisticsFormatOptions {

    private DateInterval dateInterval;
    private ExpenseGrouping grouping;

    /**
     * Private constructor to prevent instantiation.
     */
    private StatisticsFormatOptions(DateInterval dateInterval, ExpenseGrouping grouping) {
        this.dateInterval = dateInterval;
        this.grouping = grouping;
    }

    /**
     * Returns a {@code StatisticsFormatOptions} object with no changes.
     */
    public static StatisticsFormatOptions emptyOption() {
        return new StatisticsFormatOptions(null, null);
    }

    public static StatisticsFormatOptions withOptions(DateInterval dateInterval, ExpenseGrouping grouping) {
        return new StatisticsFormatOptions(dateInterval, grouping);
    }

    /**
     * @return an optional wrapper of the selected new date interval, if any.
     */
    public Optional<DateInterval> getNewDateInterval() {
        return Optional.ofNullable(dateInterval);
    }

    public Optional<ExpenseGrouping> getNewGrouping() {
        return Optional.ofNullable(grouping);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof StatisticsFormatOptions) {
            StatisticsFormatOptions other = (StatisticsFormatOptions) obj;
            return other.dateInterval == this.dateInterval
                    && other.grouping == this.grouping;
        }
        return false;
    }
}
