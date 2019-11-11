package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.AppUtil.checkArgument;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Week in a TimeTable.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Week implements Comparable<Week> {

    public static final String MESSAGE_CONSTRAINTS =
            "Week should be an integer from 1 to 13 and should not be empty.";

    public final Integer week;

    /**
     * Every field must be present and not null.
     */
    public Week(Integer week) {
        requireAllNonNull(week);
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        this.week = week;
    }

    /**
     * Returns true if a given integer is a valid week.
     */
    public static boolean isValidWeek(Integer week) {
        return week >= 1 && week <= 13;
    }

    @Override
    public String toString() {
        return week + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && week.equals(((Week) other).week)); // state check
    }

    @Override
    public int compareTo(Week week1) {
        return Integer.compare(week, week1.week);
    }

    @Override
    public int hashCode() {
        return week.hashCode();
    }
}
