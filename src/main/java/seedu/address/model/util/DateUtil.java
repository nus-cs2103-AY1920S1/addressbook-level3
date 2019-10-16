package seedu.address.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains utility methods for parsing and formatting date and time.
 */
public class DateUtil {
    /**
     * Constructs a Date object using the string supplied.
     *
     * @param rawDate the string to be parsed as a Date object in the format of dd/MM/yyyy HHmm.
     * @return the Date object parsed from the given string.
     * @throws ParseException if rawDate supplied is not in the format of dd/MM/yyyy HHmm.
     */
    public static Date parseDate(String rawDate) throws ParseException {
        Date newDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(rawDate);
        return newDate;
    }

    /**
     * Converts a Date object into the storage format of dd/MM/yyyy HHmm.
     *
     * @param date the Date object to be converted.
     * @return the formatted date string, in the format of dd/MM/yyyy HHmm.
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HHmm").format(date);
    }

    /**
     * Converts a Date object into the display format of dd MMM yyyy at HH:mm.
     *
     * @param date the Date object to be converted.
     * @return the formatted date string, in the format of dd MMM yyyy at HH:mm.
     */
    public static String formatDateForDisplay(Date date) {
        return new SimpleDateFormat("dd MMM yyyy").format(date) + " at "
                + new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * Returns true if the string supplied is in the format of dd/MM/yyyy HHmm.
     */
    public static boolean isValidDateFormat(String rawDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy HHmm").parse(rawDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
