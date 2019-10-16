package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser.
 */
public class MergeParseException extends ParseException {

    public MergeParseException(String message) {
        super(message);
    }

    public MergeParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
