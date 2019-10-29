package seedu.exercise.model.property;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents Exercise's and Statistic's date in ExerHealth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String PROPERTY_DATE = "Date";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd/MM/yyyy and must be valid.";
    public static final String MESSAGE_INVALID_END_DATE = "End date must be after start date";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public final LocalDate value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(date, formatter);
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, formatter);
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
            LocalDate sDate = LocalDate.parse(startDate, formatter);
            LocalDate eDate = LocalDate.parse(endDate, formatter);
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
            d = LocalDate.parse(date.toString(), formatter);
            sDate = LocalDate.parse(startDate.toString(), formatter);
            eDate = LocalDate.parse(endDate.toString(), formatter);
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
            sDate = LocalDate.parse(startDate.toString(), formatter);
            eDate = LocalDate.parse(endDate.toString(), formatter);
        } catch (DateTimeParseException e) {
            return -1;
        }

        return (int) DAYS.between(sDate, eDate);
    }

    /**
     * Returns today's Date.
     */
    public static Date getToday() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        return new Date(today.format(formatter));
    }

    /**
     * Returns the date of a week before today.
     */
    public static Date getOneWeekBeforeToday() {
        LocalDate today = LocalDate.parse(getToday().toString(), formatter);
        LocalDate oneWeekBefore = today.minusDays(6);
        return new Date(oneWeekBefore.format(formatter));
    }

    /**
     * Returns a list of dates between start date and end date.
     */
    public static ArrayList<Date> getListOfDates(Date startDate, Date endDate) {
        int days;
        LocalDate sDate;
        LocalDate eDate;

        try {
            sDate = LocalDate.parse(startDate.toString(), formatter);
            eDate = LocalDate.parse(endDate.toString(), formatter);
        } catch (DateTimeParseException e) {
            return new ArrayList<>();
        }
        days = (int) DAYS.between(sDate, eDate) + 1;

        ArrayList<Date> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            LocalDate temp = sDate.plusDays(i);
            Date date = new Date(temp.format(formatter));
            dates.add(date);
        }

        return dates;
    }

    @Override
    public String toString() {
        return value.format(formatter);
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
