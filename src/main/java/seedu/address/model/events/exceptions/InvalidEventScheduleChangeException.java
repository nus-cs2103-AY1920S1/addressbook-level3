//@@author SakuraBlossom
package seedu.address.model.events.exceptions;
/**
 * Represents an error which occurs during the scheduling of an {@code Event}
 */
public class InvalidEventScheduleChangeException extends Exception {
    public InvalidEventScheduleChangeException(String message) {
        super(message);
    }
}
