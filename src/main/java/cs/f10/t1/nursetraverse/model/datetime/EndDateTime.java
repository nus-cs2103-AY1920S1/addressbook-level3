package cs.f10.t1.nursetraverse.model.datetime;

import java.util.Date;

/**
 * Represents a Visit's end time in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class EndDateTime extends DateTime {

    public static final String MESSAGE_CONSTRAINTS = "End " + DateTime.MESSAGE_CONSTRAINTS_BODY;
    public static final EndDateTime UNFINISHED_VISIT_END_DATE_TIME = null;

    /**
     * Constructs an {@code EndDateTime}.
     *
     * @param dateTime A valid dateTime address.
     */
    public EndDateTime(String dateTime) {
        super(dateTime);
    }

    /**
     * Constructs a {@code DateTime}.
     *
     * @param date A valid Date.
     */
    public EndDateTime(Date date) {
        super(date);
    }

    /**
     * Returns if a given string is a valid dateTime.
     * Valid end date times must be equal or before start date time.
     */
    public static boolean isValidEndDateTime(String startDateTime, String endDateTime) {
        boolean validStartDateTime = isValidDateTime(startDateTime);
        boolean validEndDateTime = isValidDateTime(endDateTime);

        if (validStartDateTime && validEndDateTime) {
            StartDateTime start = new StartDateTime(startDateTime);
            EndDateTime end = new EndDateTime(endDateTime);
            return end.dateTime.isAfter(start.dateTime) || end.dateTime.isEqual(start.dateTime);
        } else {
            return false;
        }
    }
}
