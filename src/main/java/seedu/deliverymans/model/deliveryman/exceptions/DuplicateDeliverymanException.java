package seedu.deliverymans.model.deliveryman.exceptions;

/**
 * Signals that the operation will result in duplicate Deliverymen (Deliverymen are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDeliverymanException extends RuntimeException {
    public DuplicateDeliverymanException() {
        super("Operation would result in duplicate deliverymen");
    }
}