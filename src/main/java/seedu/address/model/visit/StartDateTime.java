package seedu.address.model.visit;

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
     * Returns if a given string is a valid dateTime.
     */
    public static boolean isValidStartDateTime(String test) {
        return isValidDateTime(test);
    }
}
