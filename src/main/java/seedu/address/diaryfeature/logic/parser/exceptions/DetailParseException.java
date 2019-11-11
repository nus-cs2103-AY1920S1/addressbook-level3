package seedu.address.diaryfeature.logic.parser.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * DetailParseException arises if there is an error in the format of the Detail
 */
public class DetailParseException extends ParseException {
    private static final String ERROR_MESSAGE = "Details have to at least 8 characters, and they can only be alphanumeric.";

    public DetailParseException() {
        super(ERROR_MESSAGE);
    }

    /**
     * @return String representation of the Error message
     */
    public String toString() {
        return ERROR_MESSAGE;
    }
}
