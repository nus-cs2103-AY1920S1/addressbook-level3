package seedu.address.model.project;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String MESSAGE_CONSTRAINTS = "Time should be written in the following format : dd/MM/yyyy hhmm and it must be written within the boundary of applicable date";
    //public static final String VALIDATION_REGEX = "\"^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4} (2[0-3]|[01]?[0-9])([0-5]?[0-9])";

    public final String time;
    private final Date date;

    public Date getDate() {
        return this.date;
    }

    public Time(String time) throws ParseException {
        requireAllNonNull(time);
        this.time = time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
        this.date = formatter.parse(time);
    }

    public static boolean isValidTime(String test) {
        //return test.matches(VALIDATION_REGEX);
        return true;
    }

    @Override
    public String toString() {
        return this.time;
    }
}
