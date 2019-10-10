package seedu.address.model.queue.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class QueueException extends RuntimeException {
    public QueueException(String message) {
        super(message);
    }
}
