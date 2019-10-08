package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's date of birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of births should only contain numbers and \".\", and it should not be blank";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d LLLL Y");

    public final LocalDate dateOfBirth;
    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);

        this.value = dateOfBirth;
        this.dateOfBirth = createLocalDate(dateOfBirth);
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        try {
            DATE_FORMAT.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return isValidDate(test);
    }

    /**
     * Returns true if the input date is valid and matches the validation regex.
     * @param test Input date of birth.
     * @return Whether the date of birth is valid.
     */
    private static boolean isValidDate(String test) {
        int day = extractDay(test);
        int month = extractMonth(test);
        int year = extractYear(test);
        if (!isValidMonth(month)) {
            return false;
        } else if (isValidDay(day, month, year)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a new {@code LocalDate} to keep track of the date of birth with respect to the system clock.
     * @param test Input date of birth.
     * @return A new {@code LocalDate}.
     */
    private static LocalDate createLocalDate(String test) {
        int day = extractDay(test);
        int month = extractMonth(test);
        int year = extractYear(test);
        return LocalDate.of(year, month, day);
    }

    /**
     * Checks if the input day in the date of birth is valid.
     * @param day Input day.
     * @param month Input month.
     * @param year Input year.
     * @return Whether the input day is valid.
     */
    private static boolean isValidDay(int day, int month, int year) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            if (day >= 1 && day <= 31) {
                return true;
            } else {
                return false;
            }
        case 4:
        case 6:
        case 9:
        case 11:
            if (day >= 1 && day <= 30) {
                return true;
            } else {
                return true;
            }
        case 2:
            if (isLeapYear(year) && day >= 1 && day <= 29) {
                return true;
            } else if (day >= 1 && day <= 28) {
                return true;
            } else {
                return false;
            }
        default:
            return false;
        }
    }

    /**
     * Checks if the input month in the date of birth is valid.
     * @param month Input month.
     * @return Whether the input month is valid.
     */
    private static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Parses the input date of birth and returns the input day as an int.
     * @param test Input date of birth.
     * @return Day in date of birth.
     */
    private static int extractDay(String test) {
        return Integer.parseInt(test.split("\\.")[0]);
    }

    /**
     * Parses the input date of birth and returns the input month as an int.
     * @param test Input date of birth.
     * @return Month in date of birth.
     */
    private static int extractMonth(String test) {
        return Integer.parseInt(test.split("\\.")[1]);
    }
    /**
     * Parses the input date of birth and returns the input year as an int.
     * @param test Input date of birth.
     * @return Year in date of birth.
     */

    private static int extractYear(String test) {
        return Integer.parseInt(test.split("\\.")[2]);
    }

    /**
     * Checks if the input year in the date of birth is a leap year.
     * @param year Input year of birth.
     * @return Whether the year is a leap year.
     */
    private static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else if (year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return dateOfBirth.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

}
