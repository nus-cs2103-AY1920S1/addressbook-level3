package seedu.weme.model.template.exceptions;

/**
 * Signals that a {@code Coordinates} object is invalid.
 */
public class InvalidCoordinatesException extends Exception {
    public InvalidCoordinatesException() {
        super("The coordinates are invalid");
    }
}
