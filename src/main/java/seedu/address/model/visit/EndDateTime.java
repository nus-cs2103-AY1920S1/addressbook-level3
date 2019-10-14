package seedu.address.model.visit;

/**
 * Represents a Visit's end time in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class EndDateTime extends DateTime {

    public static final String MESSAGE_CONSTRAINTS = "End " + DateTime.MESSAGE_CONSTRAINTS_BODY;
    public static final EndDateTime UNFINISHED_VISIT_END_DATE_TIME = null;

    /**
     * Constructs an {@code StartDateTime}.
     *
     * @param dateTime A valid dateTime address.
     */
    public EndDateTime(String dateTime) {
        super(dateTime);
    }

    /**
     * Returns if a given string is a valid dateTime.
     */
    public static boolean isValidEndDateTime(String test) {
        return isValidDateTime(test);
    }
}
