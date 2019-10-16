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

    public static final String MESSAGE_CONSTRAINTS = "Time should be written in the following format : dd/MM/yyyy hhmm";
    public static final String VALIDATION_REGEX = "^\\d{2}/\\d{2}/\\d{4} \\d{4}$";

    public final String time;
    private final Date date;

    public Time(String time) throws ParseException {
        requireAllNonNull(time);
        this.time = time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        this.date = formatter.parse(time);
    }

    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.time;
    }
}
