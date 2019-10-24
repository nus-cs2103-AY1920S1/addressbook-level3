package seedu.billboard.model.statistics.formats;

import java.util.Optional;

import seedu.billboard.commons.core.date.DateInterval;

/**
 * Encapsulates the changes in options that a user can select with regards to the display of the statistics.
 */
public class StatisticsFormatOptions {

    private DateInterval dateInterval;

    /**
     * Private constructor to prevent instantiation.
     */
    private StatisticsFormatOptions(DateInterval dateInterval) {
        this.dateInterval = dateInterval;
    }

    /**
     * Returns a {@code StatisticsFormatOptions} object with no changes.
     */
    public static StatisticsFormatOptions emptyOption() {
        return new StatisticsFormatOptions(null);
    }

    public static StatisticsFormatOptions withOptions(DateInterval dateInterval) {
        return new StatisticsFormatOptions(dateInterval);
    }

    /**
     * @return an optional wrapper of the selected new date interval, if any.
     */
    public Optional<DateInterval> getNewDateInterval() {
        return Optional.ofNullable(dateInterval);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof StatisticsFormatOptions) {
            return ((StatisticsFormatOptions) other).dateInterval == this.dateInterval;
        }
        return false;
    }
}
