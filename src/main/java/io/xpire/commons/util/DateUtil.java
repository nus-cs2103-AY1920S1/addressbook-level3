package io.xpire.commons.util;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

import io.xpire.model.item.ExpiryDate;

//@@author JermyTan
/**
 * Helper functions for handling dates.
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(ExpiryDate.DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);

    /**
     * Converts a {@code LocalDate} object to its string representation.
     * Empty string is returned if date is unable to be parsed into string.
     *
     * @param date Date object.
     * @return Date in string if conversion is successful, else empty string.
     */
    public static String convertDateToString(LocalDate date) {
        requireNonNull(date);
        try {
            return date.format(DATE_TIME_FORMATTER);
        } catch (IllegalArgumentException | DateTimeException e) {
            return "";
        }
    }

    /**
     * Converts date in string to a {@code LocalDate} object.
     * {@code null} is returned if string is unable to be parsed into date.
     *
     * @param dateInString Date in string.
     * @return Date if conversion is successful, else null.
     */
    public static LocalDate convertStringToDate(String dateInString) {
        requireAllNonNull(dateInString, DATE_TIME_FORMATTER);
        try {
            return LocalDate.parse(dateInString, DATE_TIME_FORMATTER);
        } catch (IllegalArgumentException | DateTimeException e) {
            return null;
        }
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
     * If earlierDate is later than laterDate, the negative of the difference is returned.
     *
     * @param earlierDate The earlier date.
     * @param laterDate The later date.
     * @return Difference in number of days between the 2 dates.
     * Negative of the difference is returned when earlierDate is later than laterDate.
     */
    public static long getOffsetDays(LocalDate earlierDate, LocalDate laterDate) {
        requireAllNonNull(earlierDate, laterDate);
        return ChronoUnit.DAYS.between(earlierDate, laterDate);
    }

    /**
     * Retrieves a date prior to current date by {@code offsetDays} number of days.
     *
     * @param offsetDays Number of days before current date.
     * @return Earlier date.
     */
    public static LocalDate getPreviousDate(LocalDate date, int offsetDays) {
        requireNonNull(date);
        AppUtil.checkArgument(offsetDays >= 0);
        return date.minusDays(offsetDays);
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
