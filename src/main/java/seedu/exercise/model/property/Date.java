package seedu.exercise.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Represents date in ExerHealth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String PROPERTY_DATE = "Date";
    public static final String DAYS = "day(s)";
    public static final String WEEKS = "week(s)";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd/MM/yyyy and must be valid.";
    public static final String MESSAGE_INVALID_END_DATE = "End date must be after start date";
    public static final String MESSAGE_PRETTY_PRINT_ONE_UNIT = "%1$s %2$s left...";
    public static final String MESSAGE_PRETTY_PRINT_TWO_UNITS = "%1$s and %2$s left...";

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final DateTimeFormatter STANDARD_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public final LocalDate value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(date, STANDARD_DATE_TIME_FORMATTER);
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, STANDARD_DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if given end date is before given start date.
     */
    public static boolean isEndDateAfterStartDate(String startDate, String endDate) {
        try {
            LocalDate sDate = LocalDate.parse(startDate, STANDARD_DATE_TIME_FORMATTER);
            LocalDate eDate = LocalDate.parse(endDate, STANDARD_DATE_TIME_FORMATTER);
            return eDate.compareTo(sDate) >= 0;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if given date is between start date and end date.
     */
    public static boolean isBetweenStartAndEndDate(Date date, Date startDate, Date endDate) {
        LocalDate sDate;
        LocalDate eDate;
        LocalDate d;

        try {
            d = LocalDate.parse(date.toString(), STANDARD_DATE_TIME_FORMATTER);
            sDate = LocalDate.parse(startDate.toString(), STANDARD_DATE_TIME_FORMATTER);
            eDate = LocalDate.parse(endDate.toString(), STANDARD_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        return d.compareTo(sDate) >= 0 && d.compareTo(eDate) <= 0;
    }

    /**
     * Returns the number of days between start date and end date.
     */
    public static int numberOfDaysBetween(Date startDate, Date endDate) {
        LocalDate sDate;
        LocalDate eDate;
        try {
            sDate = LocalDate.parse(startDate.toString(), STANDARD_DATE_TIME_FORMATTER);
            eDate = LocalDate.parse(endDate.toString(), STANDARD_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return -1;
        }

        return (int) ChronoUnit.DAYS.between(sDate, eDate);
    }

    /**
     * Returns today's Date.
     */
    public static Date getToday() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        return new Date(today.format(STANDARD_DATE_TIME_FORMATTER));
    }

    /**
     * Returns the date of a week before today.
     */
    public static Date getOneWeekBeforeToday() {
        LocalDate today = LocalDate.parse(getToday().toString(), STANDARD_DATE_TIME_FORMATTER);
        LocalDate oneWeekBefore = today.minusDays(6);
        return new Date(oneWeekBefore.format(STANDARD_DATE_TIME_FORMATTER));
    }

    /**
     * Returns a list of dates between start date and end date.
     */
    public static ArrayList<Date> getListOfDates(Date startDate, Date endDate) {
        int days;
        LocalDate sDate;
        LocalDate eDate;

        try {
            sDate = LocalDate.parse(startDate.toString(), STANDARD_DATE_TIME_FORMATTER);
            eDate = LocalDate.parse(endDate.toString(), STANDARD_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return new ArrayList<>();
        }
        days = (int) ChronoUnit.DAYS.between(sDate, eDate) + 1;

        ArrayList<Date> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            LocalDate temp = sDate.plusDays(i);
            Date date = new Date(temp.format(STANDARD_DATE_TIME_FORMATTER));
            dates.add(date);
        }

        return dates;
    }

    /**
     * Convenient overloaded method to pretty print a date of format dd/MM/YYYY.
     */
    public static String prettyPrint(String date) {
        requireNonNull(date);
        Date localDate = new Date(date);
        return prettyPrint(localDate);
    }

    /**
     * Returns a representation of {@code date} that is more natural to human language.
     * {@code prettyPrint} will use {@link Date#getToday()} to determine how to print the dates.
     */
    public static String prettyPrint(Date date) {
        int daysBetween = numberOfDaysBetween(Date.getToday(), date);

        //Within a week
        if (daysBetween < 7) {
            return String.format(MESSAGE_PRETTY_PRINT_ONE_UNIT, daysBetween, DAYS);
        } else {
            int numOfWeeksLeft = daysBetween / 7;
            int numOfDaysLeft = daysBetween % 7;

            // A full week distance away. Don't show xxx days remaining.
            if (numOfDaysLeft == 0) {
                return String.format(MESSAGE_PRETTY_PRINT_ONE_UNIT, numOfWeeksLeft, WEEKS);
            } else {
                return String.format(MESSAGE_PRETTY_PRINT_TWO_UNITS, numOfWeeksLeft + " " + WEEKS,
                        numOfDaysLeft + " " + DAYS);
            }
        }
    }

    /**
     * Returns the dd/MM/YYYY representation of the date.
     * Call {@link Date#prettyPrint} to return a more natural form of
     * the date.
     */
    @Override
    public String toString() {
        return value.format(STANDARD_DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles nulls
            && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
