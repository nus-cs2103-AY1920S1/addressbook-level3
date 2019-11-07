package dukecooks.logic.parser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import dukecooks.model.Date;
import dukecooks.model.Time;

/**
 * Parses any String object as a Date object.
 * Capable of string conversions to date and Date validation checks.
 */
public class DateParser {

    private static final DateTimeFormatter datePatternFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter timePatternFormat = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter datetimePatternFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static long getCurrentDayDiff(Date date) {
        LocalDate current = LocalDate.parse(getCurrentDate(), datePatternFormat);
        LocalDate other = LocalDate.parse(date.toString(), datePatternFormat);

        return Duration.between(current.atStartOfDay(), other.atStartOfDay()).toDays();
    }

    public static long getCurrentTimeDiff(Time time) {
        LocalTime current = LocalTime.parse(getCurrentTime(), timePatternFormat);
        LocalTime other = LocalTime.parse(time.toString(), timePatternFormat);

        return Duration.between(current, other).toMinutes();
    }

    /**
     * Supports comparing two dates to determine which is the more recent one.
     */
    public static int compareDate(Date date1, Date date2) {
        LocalDate newDate1 = LocalDate.parse(date1.toString(), datePatternFormat);
        LocalDate newDate2 = LocalDate.parse(date2.toString(), datePatternFormat);
        return newDate1.compareTo(newDate2);
    }

    /**
     * Supports comparing time to determine which is the more recent one.
     */
    public static int compareTime(Time time1, Time time2) {
        LocalTime newTime1 = LocalTime.parse(time1.toString(), timePatternFormat);
        LocalTime newTime2 = LocalTime.parse(time2.toString(), timePatternFormat);
        return newTime1.compareTo(newTime2);
    }

    public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        return datePatternFormat.format(now);
    }

    public static String getCurrentTime() {
        LocalTime now = LocalTime.now();
        return timePatternFormat.format(now);
    }

    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return datetimePatternFormat.format(now);
    }
}
