package seedu.address.calendar.model.date;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

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
     * Checks whether the given {@code String} year can represent a valid year.
     *
     * @param year Year to check
     * @return {@code true} if and only if {@code year} is a representation of a valid year
     */
    public static boolean isValidYear(String year) {
        String trimmedYear = year.trim();
        try {
            int yearInt = Integer.parseInt(trimmedYear);
            return isValidYear(yearInt);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether the given {@code int} year is between 1980 and 2200.
     *
     * @param year Year to check
     * @return {@code true} if and only if {@code year} is between 1980 and 2200 (inclusive)
     */
    public static boolean isValidYear(int year) {
        return year >= BOUND_LOWER && year <= BOUND_UPPER;
    }

    /**
     * Checks whether {@code this} is a leap year.
     *
     * @return {@code true} if and only if {@code this} is a leap year
     */
    public boolean isLeapYear() {
        if ((year % 4 == 0) && (year % 100 != 0)) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        } else {
            return false;
        }
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
