package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Helper functions for handling LocalDates.
 */
public class DateUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Get current system date.
     *
     * @return the current date from the system clock in the default time-zone.
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    /**
     * Get current date in formatted string.
     *
     * @return Formatted string representation of today's date.
     */
    public static String getTodayFormattedDate() {
        return formatDate(getTodayDate());
    }

    /**
     * Get the date that is number of days after starting date.
     * {@code days} must be non-negative.
     *
     * @param startDate Starting date.
     * @param days Number of days from starting date.
     * @return Date that is number of days after starting date.
     */
    public static LocalDate extendDate(LocalDate startDate, int days) {
        requireNonNull(startDate);
        assert days >= 0 : "days must be positive";

        return startDate.plusDays(days);
    }

    /**
     * Get the date that is number of days before starting date. For tests and simulation purposes.
     *
     * @param days Number of days from starting date.
     * @return Date that is number of days before starting date.
     */
    public static LocalDate getTodayMinusDays(int days) {
        return getTodayDate().minusDays(days);
    }

    /**
     * Get the date that is number of days after today.
     * {@code days} must be non-negative.
     *
     * @param days Number of days from today.
     * @return Date that is number of days after today.
     */
    public static LocalDate getTodayPlusDays(int days) {
        assert days >= 0 : "days must be positive";

        return extendDate(getTodayDate(), days);
    }

    /**
     * Get the number of days between startDate and endDate.
     * {@code endDate} must be later than {@code startDate}.
     *
     * @param startDate Starting date.
     * @param endDate Ending date.
     * @return Number of days between the two dates.
     */
    public static int getNumOfDaysBetween(LocalDate startDate, LocalDate endDate) {
        requireAllNonNull(startDate, endDate);
        assert endDate.isAfter(startDate) : "endDate should be later than startDate";

        return (int) startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * Get the number of days overdue.
     *
     * @param dueDate Due date of the book.
     * @param returnDate Date book is returned.
     * @return 0 if book is not overdue, else the number of days overdue.
     */
    public static int getNumOfDaysOverdue(LocalDate dueDate, LocalDate returnDate) {
        requireAllNonNull(dueDate, returnDate);
        if (!returnDate.isAfter(dueDate)) {
            return 0;
        } else {
            return getNumOfDaysBetween(dueDate, returnDate);
        }
    }

    /**
     * Returns true if given date is before today's date.
     *
     * @param date Given date to be checked.
     * @return True if given date is before today's date.
     */
    public static boolean isDateBeforeToday(LocalDate date) {
        requireNonNull(date);
        return date.isBefore(getTodayDate());
    }

    /**
     * Formats the date.
     *
     * @param date date to be formatted.
     * @return a formatted date as a {@code String}.
     */
    public static String formatDate(LocalDate date) {
        return date.format(formatter);
    }
}
