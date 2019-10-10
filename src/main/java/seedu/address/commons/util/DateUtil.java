package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Helper functions for handling LocalDates.
 */
public class DateUtil {
    /**
     * Get current system date.
     *
     * @return the current date from the system clock in the default time-zone.
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    /**
     * Get the date that is number of days after starting date.
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
     *
     * @param startDate Starting date.
     * @param endDate Ending date.
     * @return Number of days between the two dates.
     */
    public static int getNumOfDaysBetween(LocalDate startDate, LocalDate endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        assert endDate.isAfter(startDate) : "endDate should be later than startDate";

        return (int) startDate.until(endDate, ChronoUnit.DAYS);
    }
}
