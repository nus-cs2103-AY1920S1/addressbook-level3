package seedu.address.person.logic.parser.exceptions;

import seedu.address.person.commons.exceptions.IllegalValueException;

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

    @Override
    public String toString() {
        return this.message;
    }
}
