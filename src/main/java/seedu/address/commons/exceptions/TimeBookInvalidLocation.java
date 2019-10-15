package seedu.address.commons.exceptions;
/**
 * Signals that some given data does not location requirements
 */
public class TimeBookInvalidLocation extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TimeBookInvalidLocation(String message) {
        super(message);
    }
}
