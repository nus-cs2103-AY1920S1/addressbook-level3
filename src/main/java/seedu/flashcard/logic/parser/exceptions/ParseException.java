package seedu.flashcard.logic.parser.exceptions;

import seedu.flashcard.commons.exceptions.IllegalValueException;

/**
 * Represent a parser error encountered by the parser.
 */
public class ParseException extends IllegalValueException {

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
