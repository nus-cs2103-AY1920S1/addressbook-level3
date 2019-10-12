package seedu.billboard.commons.core.date;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import seedu.billboard.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents a immutable {@code DateRange} from {@code startDate} to {@code endDate}.
 */
public class DateRange {

    private static final String INVALID_DATES_MESSAGE = "End date should not be before start date!";

    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Creates a new date range starting from {@code startDate} to {@code endDate}.
     * @param startDate The starting date.
     * @param endDate The ending date.
     */
    public DateRange(LocalDate startDate, LocalDate endDate) throws IllegalValueException {
        requireAllNonNull(startDate, endDate);
        if (endDate.isBefore(startDate)) {
            throw new IllegalValueException(INVALID_DATES_MESSAGE);
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Getter for start date.
     * @return Start date, as a {@code LocalDate}.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Getter for end date.
     * @return End date, as a {@code LocalDate}.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Creates a list of new ranges by splitting the current {@code DateRange} into smaller ranges based on the given
     * interval. The ranges returned are sequential, and the first range starts at the previous human logical start
     * date for the given interval. For example, if {@code DateInterval.MONTH} is passed in, the first date range will
     * start on the first day of the month.
     * If the date range cannot be divided evenly into the smaller intervals, the final element of the list will
     * contain the {@code endDate} in its date range, ie. it is end inclusive.
     *
     * @param interval Specified interval to divide the date range by.
     * @return List of date ranges that span the same duration as the given intervals, from the start date to end date,
     *         possible exceeding the end date.
     */
    public List<DateRange> partitionByInterval(DateInterval interval) {
        LocalDate closestStart = startDate.with(interval.getAdjuster());

        return closestStart.datesUntil(endDate, interval.getPeriod())
                .map(start -> dateRangeOverPeriod(start, interval.getPeriod()))
                .collect(Collectors.toList());
    }

    private DateRange dateRangeOverPeriod(LocalDate startDate, Period period) {
        try {
            return new DateRange(startDate, startDate.plus(period).minus(Period.ofDays(1)));
        } catch (IllegalValueException e) {
            // should never happen
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true; // short circuit if same object
        } else if (obj instanceof DateRange) {
            DateRange other = (DateRange) obj;
            return startDate.equals(other.startDate) && endDate.equals(other.endDate);
        }

        return false;
    }
}
