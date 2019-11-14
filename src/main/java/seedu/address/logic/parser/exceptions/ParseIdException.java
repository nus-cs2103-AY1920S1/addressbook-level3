package seedu.address.logic.parser.exceptions;

/**
 * Extends the ParseException class and represents exceptional cases encountered
 * when parsing the entity ID that a user specifies.
 */
public class ParseIdException extends ParseException {

    public ParseIdException(String message) {
        super(message);
    }

    public ParseIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
