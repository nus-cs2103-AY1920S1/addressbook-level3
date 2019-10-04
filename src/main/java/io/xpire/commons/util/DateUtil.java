package io.xpire.commons.util;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;

/**
 * Helper functions for handling dates.
 */
public class DateUtil {
    private static final Logger logger = LogsCenter.getLogger(DateUtil.class);

    /**
     * Converts a {@code LocalDate} object to its string representation.
     * Empty string is returned if date is unable to be parsed into string.
     *
     * @param date Date object.
     * @param dateFormat String format of the date.
     * @return Date in string if conversion is successful, else empty string.
     */
    public static String convertDateToString(LocalDate date, String dateFormat) {
        requireAllNonNull(date, dateFormat);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return date.format(formatter);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to recognise date format : " + StringUtil.getDetails(e));
        } catch (DateTimeException e) {
            logger.warning("Failed to parse date to string : " + StringUtil.getDetails(e));
        }
        return "";
    }

    /**
     * Converts date in string to a {@code LocalDate} object.
     * {@code null} is returned if string is unable to be parsed into date.
     *
     * @param dateInString Date in string.
     * @param dateFormat String format of the date.
     * @return Date if conversion is successful, else null.
     */
    public static LocalDate convertStringToDate(String dateInString, String dateFormat) {
        requireAllNonNull(dateInString, dateFormat);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return LocalDate.parse(dateInString, formatter);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to recognise date format : " + StringUtil.getDetails(e));
        } catch (DateTimeException e) {
            logger.warning("Failed to parse string to date : " + StringUtil.getDetails(e));
        }
        return null;
    }

    /**
     * Checks if 2 dates are within input {@code days}.
     * If the {@code earlierDate} is later than {@code laterDate}, then {@code true} is returned.
     *
     * @param days Offset range between the 2 dates.
     * @param earlierDate The earlier date.
     * @param laterDate The later date.
     * @return {@code true} if 2 dates are within range or if earlierDate is later than laterDate, else {@code false}
     */
    public static boolean isWithinRange(int days, LocalDate earlierDate, LocalDate laterDate) {
        requireAllNonNull(earlierDate, laterDate);
        AppUtil.checkArgument(days >= 0);
        return earlierDate.plusDays(days).compareTo(laterDate) >= 0;
    }

    /**
     * Retrieves the difference in the number of days between the {@code earlierDate} and the {@code laterDate}.
     * If earlierDate is later than laterDate, 0 is returned.
     *
     * @param earlierDate The earlier date.
     * @param laterDate The later date.
     * @return Difference in number of days between the 2 dates. 0 is returned when earlierDate is later than laterDate.
     */
    public static int getOffsetDays(LocalDate earlierDate, LocalDate laterDate) {
        requireAllNonNull(earlierDate, laterDate);
        int offset = earlierDate.until(laterDate).getDays();
        return Math.max(offset, 0);
    }

    /**
     * Retrieves the current date according to local system clock.
     *
     * @return Current date.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
