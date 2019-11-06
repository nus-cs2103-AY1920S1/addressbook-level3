package seedu.elisa.logic.parser.exceptions;

/**
 * Represents a parse error encountered by parsing a non-existent date
 */
public class InvalidDateException extends ParseException {

    public InvalidDateException() {
        super("This date does not exist!");
    }
}
