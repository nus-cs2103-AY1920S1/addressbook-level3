package seedu.tarence.logic.parser.exceptions;

/**
 * Represents a specific instance of a {@code ParseException} where the input is a parsable integer but is <= 0.
 */
public class NegativeIndexException extends ParseException {

    public NegativeIndexException(String message) {
        super(message);
    }
}
