package seedu.address.diaryfeature.model.diaryEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeFormatter class takes in an input string of a date and outputs it in the proper.
 * Date/Time format, specified by the Java Date class
 */

public class DateFormatter {
    /**
     * Returns the Date in proper date format, after parsing the user input version of a date.
     *
     * @param str String input of user specified date
     * @return Date specified by user.
     */

    public static java.util.Date convertToDate(String str) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return myFormatter.parse(str);
    }

    /**
     * Returns the String format of a date.
     *
     * @param idea input Date
     * @return String format of date for easier input/ouput
     */

    public static String convertToStringStore(Date idea) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return myFormatter.format(idea);
    }

    /**
     * Returns the String format of a date.
     *
     * @param idea input Date
     * @return String format of date for easier reading
     */


    public static String convertToStringPrint(Date idea) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return myFormatter.format(idea);
    }
}
