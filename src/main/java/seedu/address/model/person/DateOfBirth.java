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

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d LLLL Y");

    public final LocalDate dateOfBirth;

    /**
     * Constructs a {@code Name}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.dateOfBirth = createLocalDate(dateOfBirth);
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        try {
            dateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return isValidDate(test);
    }

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

    private static LocalDate createLocalDate(String test) {
        int day = extractDay(test);
        int month = extractMonth(test);
        int year = extractYear(test);
        return LocalDate.of(year, month, day);
    }

    public static boolean isValidDay(int day, int month, int year) {
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

    private static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    private static int extractDay(String test) {
        return Integer.parseInt(test.split("/")[0]);
    }

    private static int extractMonth(String test) {
        return Integer.parseInt(test.split("/")[1]);
    }

    private static int extractYear(String test) {
        return Integer.parseInt(test.split("/")[2]);
    }

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
        return dateOfBirth.format(dateFormatter);
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
