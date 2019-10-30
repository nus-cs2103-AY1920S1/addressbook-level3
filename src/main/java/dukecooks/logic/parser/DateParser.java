package dukecooks.logic.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Parses any String object as a Date object.
 * Capable of string conversions to date and Date validation checks.
 */
public class DateParser {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static long getCurrentDayDiff(String date) {
        try {
            Date current = simpleDateFormat.parse(getCurrentDate());
            Date other = simpleDateFormat.parse(date);

            long diffInMillies = Math.abs(current.getTime() - other.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Supports comparing two dates to determine which is the more recent one.
     */
    public static int compareDate(String date1, String date2) {
        LocalDate newDate1 = LocalDate.parse(date1, dateFormat);
        LocalDate newDate2 = LocalDate.parse(date2, dateFormat);
        return newDate1.compareTo(newDate2);
    }

    /**
     * Supports comparing time to determine which is the more recent one.
     */
    public static int compareTime(String time1, String time2) {
        LocalTime newTime1 = LocalTime.parse(time1, timeFormat);
        LocalTime newTime2 = LocalTime.parse(time2, timeFormat);
        return newTime1.compareTo(newTime2);
    }

    /**
     * Returns true if given string is a valid date of specified dateFormat
     */
    public static boolean isValidDate(String date) {
        try {
            dateFormat.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if given string is a valid datetime of specified datetimeFormat
     */
    public static boolean isValidDateTime(String datetime) {
        try {
            datetimeFormat.parse(datetime);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        return dateFormat.format(now);
    }

    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return datetimeFormat.format(now);
    }
}
