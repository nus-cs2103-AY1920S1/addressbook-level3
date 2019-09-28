package seedu.address.commons.util;

import java.time.LocalDate;

/**
 * Helper functions for handling LocalDates.
 */
public class DateUtil {
    /**
     * Get current system date.
     *
     * @return the current date from the system clock in the default time-zone.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
