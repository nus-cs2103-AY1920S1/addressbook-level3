package seedu.savenus.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

//@@author raikonen
/**
 * Time Formatter class to format {@code LocalDateTime} input into user-readable strings.
 */
public class TimeFormatter {
    /**
     * Format input time and calculate the number of days until now.
     * i.e. 0 for same day, 1 for yesterday ...
     * @param inputTimeInLocalDateTime Input time
     */
    public static long getDaysAgo(LocalDateTime inputTimeInLocalDateTime) {
        return getDaysAgo(inputTimeInLocalDateTime, LocalDateTime.now());
    }

    /**
     * Format input times and compare them to get number of days between them.
     * i.e. 0 for same day, 1 for yesterday ...
     * @param startTimeInLocalDateTime Input time
     */
    public static long getDaysAgo(LocalDateTime startTimeInLocalDateTime, LocalDateTime endTimeInLocalDateTime) {
        LocalDate inputTimeInLocalDate = startTimeInLocalDateTime.toLocalDate();
        return ChronoUnit.DAYS.between(inputTimeInLocalDate, endTimeInLocalDateTime);
    }

    /**
     * Format to hh:mm a.
     * @param inputTimeInLocalDateTime
     */
    public static String format12HourClock(LocalDateTime inputTimeInLocalDateTime) {
        return inputTimeInLocalDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    /**
     * Format to EEE, dd MMM.
     * @param inputTimeInLocalDateTime
     */
    public static String formatDayPlusDate(LocalDateTime inputTimeInLocalDateTime) {
        return inputTimeInLocalDateTime.format(DateTimeFormatter.ofPattern("EEE, dd MMM"));
    }
}
