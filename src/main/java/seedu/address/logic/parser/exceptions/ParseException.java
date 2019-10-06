package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 * ParseException is thrown is one of the 4 scenario:
 * 1) When the command is unknown
 * 2) When the specific operation cannot be applied on an entity(invalid entity)
 * 3) When the command format is inaccurate
 * 4) When user input is incomplete.
 *
 */
public class ParseException extends IllegalValueException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
