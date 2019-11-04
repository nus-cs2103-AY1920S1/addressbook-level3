package seedu.algobase.model.searchrule.plansearchrule;

import static seedu.algobase.commons.util.AppUtil.checkArgument;
import static seedu.algobase.logic.parser.ParserUtil.FORMATTER;

import java.time.LocalDate;

/**
 * Represents a {@code TimeRangePredicate}'s starting date and end date.
 */
public class TimeRange {

    public static final String MESSAGE_CONSTRAINTS = "Both start date and end date should be present";
    public static final String ORDER_CONSTRAINTS = "Starting time should not be after end time.";

    public final LocalDate startDate;
    public final LocalDate endDate;

    /**
     * Constructs a {@code TimeRange}.
     *
     * @param startDate starting date of time range
     * @param endDate end date of time range
     */
    public TimeRange(LocalDate startDate, LocalDate endDate) {
        checkArgument(isValidRange(startDate, endDate), MESSAGE_CONSTRAINTS);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns true if a given time range is valid.
     */
    public static boolean isValidRange(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

    @Override
    public String toString() {
        return startDate.format(FORMATTER) + " " + endDate.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TimeRange
                && startDate.equals(((TimeRange) other).startDate)
                && endDate.equals(((TimeRange) other).endDate));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean hasOverlap(TimeRange other) {
        return this.startDate.isBefore(other.endDate) && other.startDate.isBefore(this.endDate);
    }
}
