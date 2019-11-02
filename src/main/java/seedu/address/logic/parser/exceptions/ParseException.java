package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    private String message;
    public ParseException(String message) {
        super(message);
        this.message = message;
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
