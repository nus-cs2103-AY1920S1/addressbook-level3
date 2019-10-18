package seedu.address.model.datetime;

import seedu.address.commons.exceptions.IllegalValueException;

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
     * Returns if a given string is a valid endDateTime.
     */
    public static boolean isValidEndDateTime(String endDateTime, String startDateTime) {
        boolean validStartDateTime = isValidDateTime(startDateTime);
        boolean validEndDateTime = isValidDateTime(endDateTime);

        if (validStartDateTime && validEndDateTime) {
            StartDateTime start = new StartDateTime(startDateTime);
            EndDateTime end = new EndDateTime(endDateTime);
            return end.dateTime.isAfter(start.dateTime);
        } else {
            return false;
        }
    }
}
