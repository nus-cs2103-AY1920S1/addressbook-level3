package seedu.address.model.queue.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class RoomException extends RuntimeException {
    public RoomException(String message) {
        super(message);
    }
}
