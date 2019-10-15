package seedu.address.commons.exceptions;
/**
 * Signals that some given data does not fulfill the current state at which it is called at
 */
public class TimeBookInvalidState extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TimeBookInvalidState(String message) {
        super(message);
    }
}
