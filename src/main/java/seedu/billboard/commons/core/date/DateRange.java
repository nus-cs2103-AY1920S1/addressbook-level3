package seedu.billboard.commons.core.date;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Represents a immutable {@code DateRange} from {@code startDate} to {@code endDate}.
 */
public class DateRange {

    private static final String INVALID_DATES_MESSAGE = "End date should not be before start date.";

    private final LocalDate startDate;
    private final LocalDate endDate;


    private DateRange(LocalDate startDate, LocalDate endDateExclusive) throws DateTimeException {
        requireAllNonNull(startDate, endDateExclusive);
        if (endDateExclusive.isBefore(startDate)) {
            throw new DateTimeException(INVALID_DATES_MESSAGE);
        }

        this.startDate = startDate;
        this.endDate = endDateExclusive;
    }

    /**
     * Creates a new date range starting from {@code startDate} to {@code endDate} exclusive.
     *
     * @param startDate The starting date.
     * @param endDateExclusive  The ending date, exclusive
     * @throws DateTimeException if the end date is before the start date.
     */
    public static DateRange from(LocalDate startDate, LocalDate endDateExclusive) throws DateTimeException {
        return new DateRange(startDate, endDateExclusive);
    }

    /**
     * Creates a new date range starting from {@code startDate} to {@code endDate} inclusive.
     *
     * @param startDate The starting date.
     * @param endDateInclusive  The ending date, inclusive
     * @throws DateTimeException if the end date is before the start date.
     */
    public static DateRange fromClosed(LocalDate startDate, LocalDate endDateInclusive) throws DateTimeException {
        return new DateRange(startDate, endDateInclusive.plus(Period.ofDays(1)));
    }

    /**
     * Creates a date range starting at the given start date, and lasts for as long as the given period. Only accepts
     * positive {@code Period} values. Negative {@code Period} values will throw an exception.
     *
     * @param startDate Given start date.
     * @param period    Period the date range should last for.
     * @return the newly created date range.
     * @throws DateTimeException if the period given is negative.
     */
    public static DateRange overPeriod(LocalDate startDate, Period period) throws DateTimeException {
        if (period.isNegative()) {
            throw new DateTimeException("Period cannot be negative.");
        }
        return new DateRange(startDate, startDate.plus(period));
    }

    /**
     * Getter for start date.
     *
     * @return Start date, as a {@code LocalDate}.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Getter for end date exclusive.
     * @return End date, as a {@code LocalDate}.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Getter for end date inclusive.
     *
     * @return End date, as a {@code LocalDate}.
     */
    public LocalDate getEndDateInclusive() {
        return endDate.minus(Period.ofDays(1));
    }

    /**
     * Checks if the given date lies within this {@code DateRange}
     * @param date Date to be checked.
     * @return true if the date lies within the date range, false otherwise.
     */
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(getEndDateInclusive());
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
     * possible exceeding the end date.
     */
    public List<DateRange> partitionByInterval(DateInterval interval) {
        LocalDate closestStart = startDate.with(interval.getAdjuster());

        return closestStart.datesUntil(endDate, interval.getPeriod())
                .map(start -> overPeriod(start, interval.getPeriod()))
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDate, endDate);
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
