package cs.f10.t1.nursetraverse.model.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidStartDateTime(String test) {
        return isValidDateTime(test);
    }

    /**
     * Returns true if a given string is dateTime after the system's dateTime.
     */
    public static boolean isAfterSystemDateTime(String test) {
        LocalDateTime startDateTime = parseDateTime(test);
        LocalDateTime systemDateTime = LocalDateTime.now(ZoneId.systemDefault());
        return startDateTime.isAfter(systemDateTime);
    }
}
