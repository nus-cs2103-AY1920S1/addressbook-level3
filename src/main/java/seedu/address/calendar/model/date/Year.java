package seedu.address.calendar.model.date;

import java.util.Arrays;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents year.
 * Guarantees: year is between 1980 and 2200, immutable.
 */
public class Year implements Comparable<Year> {
    public static final String MESSAGE_CONSTRAINTS = "Invalid year. Year must be between 1980 and 2200";
    public static final int BOUND_LOWER = 1980;
    public static final int BOUND_UPPER = 2200;
    private int year;

    /**
     * Constructs a {@code Year}.
     *
     * @param year Any {@code int} between 1980 and 2200 (inclusive)
     */
    public Year(int year) {
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        this.year = year;
    }

    /**
     * Gets the associated numerical value of {@code this}.
     *
     * @return Numerical value of {@code this}
     */
    public int getNumericalValue() {
        return year;
    }

    /**
     * Returns a copy of {@code this}.
     *
     * @return A copy of {@code this}.
     */
    Year copy() {
        return new Year(year);
    }

    /**
     * Checks whether the given {@code int} year is between 1980 and 2200.
     *
     * @param year Year to check
     * @return {@code true} if {@code year} is between 1980 and 2200 (inclusive)
     */
    public static boolean isValidYear(int year) {
        return year >= BOUND_LOWER && year <= BOUND_UPPER;
    }

    @Override
    public int compareTo(Year otherYear) {
        return year - otherYear.year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Year)) {
            return false;
        }

        Year otherYear = (Year) obj;
        boolean isSameYear = this.year == otherYear.year;
        return isSameYear;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{year});
    }
}
