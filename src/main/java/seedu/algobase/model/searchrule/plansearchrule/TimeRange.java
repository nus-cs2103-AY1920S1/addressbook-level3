package seedu.algobase.model.searchrule.plansearchrule;

import static seedu.algobase.commons.util.AppUtil.checkArgument;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.ParserUtil.FORMATTER;

import java.time.LocalDate;

/**
 * Represents a {@code TimeRangePredicate}'s starting date and end date.
 */
public class TimeRange {

    public static final String MESSAGE_CONSTRAINTS = "Both start date and end date should be present.";

    public final LocalDate startDate;
    public final LocalDate endDate;

    /**
     * Constructs a {@code TimeRange}.
     *
     * @param
     */
    public TimeRange(LocalDate startDate, LocalDate endDate) {
        requireAllNonNull(startDate, endDate);
        checkArgument(isValidRange(startDate, endDate), MESSAGE_CONSTRAINTS);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns true if a given time range is valid.
     */
    public static boolean isValidRange(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null;
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
}
