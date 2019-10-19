package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's dateOfBirth number in the user's biography data.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format DD/MM/YYYY, should only contain numbers. "
                    + "Please also check that the number of days in a month (including those for leap years), "
                    + "and the number of months in a year are correct.";
    public static final String VALIDATION_REGEX = "^$|^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
    public final String dateOfBirth;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dateOfBirth A valid dateOfBirth number.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns whether a given year is a leap year.
     * @param year Integer year between 0-9999.
     * @return A boolean of whether or not the given year is a leap year.
     */
    private static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid dateOfBirth number.
     */
    public static boolean isValidDateOfBirth(String test) {
        if (test.equals("")) {
            return true;
        } else if (!test.matches(VALIDATION_REGEX)) {
            return false;
        } else if (getDateToken(test) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns a validated three-item array representing the date.
     * @param dateString String representation of date.
     * @return An integer array with integers representing day, month and year respectively.
     */
    private static int[] getDateToken(String dateString) {
        String[] dateTokens = dateString.split("/");
        if (dateTokens.length != 3) {
            return null;
        }

        int day = 0;
        int month = 0;
        int year = 0;

        try {
            day = Integer.parseInt(dateTokens[0]);
            month = Integer.parseInt(dateTokens[1]);
            year = Integer.parseInt(dateTokens[2]);

            if (day < 0 || month < 0 || month > 12 || year < 0) {
                return null;
            }

            if ((month <= 7 && month % 2 != 0)
                    || (month > 7 && month % 2 == 0)) {
                if (day > 31) {
                    return null;
                }
            } else {
                if (month == 2 && isLeapYear(year)) {
                    if (day > 29) {
                        return null;
                    }
                } else if (month == 2 && !isLeapYear(year)) {
                    if (day > 28) {
                        return null;
                    }
                } else {
                    if (day > 30) {
                        return null;
                    }
                }
            }
            return new int[]{day, month, year};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return dateOfBirth;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && dateOfBirth.equals(((DateOfBirth) other).dateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

}
