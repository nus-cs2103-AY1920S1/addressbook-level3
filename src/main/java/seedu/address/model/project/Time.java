package seedu.address.model.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Time {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */


    public static final String MESSAGE_CONSTRAINTS = "Time should be written in the following format : dd/MM/yyyy hhmm";
    public static final String DATE_CONSTRAINTS = "the date in the format of dd/MM/yyyy hhmm should be written in the following range: \n"
            + "-  MM between 01-12 inclusive\n"
            + "-  dd between 0-31 inclusive in the months of Jan(01), Mar(03), May(05), July(07), Aug(08), Oct(10), Dec(12)\n"
            + "-  dd between 0-30 inclusive in the months of Apr(04), June(06), Sep(09), Nov(11)\n"
            + "-  between 0-28 in Feb(02) in non-leap year\n"
            + "-  between 0-29 in Feb(02) in leap year";
    public static final String TIME_CONSTRAINTS = "The time should be written with a range from 0000 to 2359";
    //public static final String VALIDATION_REGEX = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d (2[0-3]|[01]?[0-9])([0-5]?[0-9])$";
    public static final String VALIDATION_REGEX = "^\\d{2}/\\d{2}/\\d{4} \\d{4}$";
    public static final String DATE_VALIDATION_REGEX = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
    public static final String TIME_VALIDATION_REGEX = "(((0|1)[0-9])|(2[0-3]))[0-5][0-9]";

    public final String time;
    private final Date date;

    public Time(String time) throws ParseException {
        requireAllNonNull(time);
        this.time = time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
        this.date = formatter.parse(time);
    }

    public Date getDate() {
        return this.date;
    }

    public static boolean isValidTimeAndDate(String time) {
        return time.matches(VALIDATION_REGEX);
    }

    public static boolean isValidTime(String time) {
        return time.matches(TIME_VALIDATION_REGEX);
    }

    public static boolean isValidDate(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, date);
    }
}
