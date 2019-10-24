package seedu.billboard.model.statistics.formats;

import java.util.function.UnaryOperator;

import seedu.billboard.commons.core.date.DateInterval;

/**
 * Encapsulates the changes in options that a user can select with regards to the display of the statistics.
 */
public class StatisticsFormatChangeOptions {
    private UnaryOperator<DateInterval> dateIntervalChange;

    /**
     * Initializes a {@code StatisticsFormatChangeOptions} object with no changes.
     */
    public StatisticsFormatChangeOptions() {
    }

    /**
     * Initializes a {@code StatisticsFormatChangeOptions} with the specified changes. A null value for any parameter
     * means that there are no changes for that option.
     * @param dateInterval New date interval to be used for any relevant statistics format.
     */
    public StatisticsFormatChangeOptions(DateInterval dateInterval) {
        if (dateInterval == null) {
            dateIntervalChange = x -> x;
        } else {
            dateIntervalChange = x -> dateInterval;
        }
    }

    public DateInterval getNewDateInterval(DateInterval oldDateInterval) {
        return dateIntervalChange.apply(oldDateInterval);
    }
}
