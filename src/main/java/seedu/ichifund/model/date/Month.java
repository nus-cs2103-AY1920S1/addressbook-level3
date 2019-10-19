package seedu.ichifund.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents a Month in a Date in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Month implements Comparable<Month> {
    public static final String MESSAGE_CONSTRAINTS =
            "Month should only contain numbers, from 1 to 12";
    public static final String VALIDATION_REGEX = "[1-9]|1[0-2]";
    public static final int[] MONTHS_30_DAYS = {4, 6, 9, 11};
    public static final int[] MONTHS_31_DAYS = {1, 3, 5, 7, 8, 10, 12};
    public final int monthNumber;

    /**
     * Constructs a {@code Month}.
     *
     * @param month A valid month number.
     */
    public Month(String month) {
        requireNonNull(month);
        checkArgument(isValidMonth(month), MESSAGE_CONSTRAINTS);
        monthNumber = Integer.parseInt(month);
    }

    /**
     * Returns true if a given string is a valid month.
     */
    public static boolean isValidMonth(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static Month getCurrent() {
        return new Month(Integer.toString(LocalDate.now().getMonth().getValue()));
    }

    /**
     * Returns true if the month has 30 days.
     */
    public boolean has30Days() {
        for (int i : MONTHS_30_DAYS) {
            if (i == monthNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the month has 31 days.
     */
    public boolean has31Days() {
        for (int i : MONTHS_31_DAYS) {
            if (i == monthNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the word representation of the {@code Month}.
     */
    public String wordString() {
        switch(monthNumber) {
        case 1:
            return "January";
        case 2:
            return "February";
        case 3:
            return "March";
        case 4:
            return "April";
        case 5:
            return "May";
        case 6:
            return "June";
        case 7:
            return "July";
        case 8:
            return "August";
        case 9:
            return "September";
        case 10:
            return "October";
        case 11:
            return "November";
        case 12:
            return "December";
        default:
            return "!all";
        }
    }

    @Override
    public String toString() {
        return "" + monthNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Month // instanceof handles nulls
                && monthNumber == (((Month) other).monthNumber)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(Month other) {
        // Later months are given priority
        return other.monthNumber - monthNumber;
    }
}
