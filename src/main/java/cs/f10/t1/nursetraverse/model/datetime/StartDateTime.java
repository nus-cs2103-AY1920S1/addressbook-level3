package cs.f10.t1.nursetraverse.model.datetime;

import java.util.Date;

/**
 * Represents a Visit's end time in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class StartDateTime extends DateTime {

    public static final String MESSAGE_CONSTRAINTS = "Start " + DateTime.MESSAGE_CONSTRAINTS_BODY;

    /**
     * Constructs an {@code StartDateTime}.
     *
     * @param dateTime A valid dateTime address.
     */
    public StartDateTime(String dateTime) {
        super(dateTime);
    }

    /**
     * Constructs a {@code DateTime}.
     *
     * @param date A valid Date.
     */
    public StartDateTime(Date date) {
        super(date);
    }

    /**
     * Returns true if a given string is a valid date time.
     */
    public static boolean isValidStartDateTime(String test) {
        return isValidDateTime(test);
    }

}
