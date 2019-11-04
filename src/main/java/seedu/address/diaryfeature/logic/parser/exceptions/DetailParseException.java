package seedu.address.diaryfeature.logic.parser.exceptions;

/**
 * DetailParseException arises if there is an error in the format of the Detail
 */
public class DetailParseException extends Exception {
    private final String ERROR_MESSAGE = "Details have to at least 8 characters, and they can only be alphanumeric!";

    /**
     *
     * @return String representation of the Error message
     */
    public String toString() {
        return ERROR_MESSAGE;
    }
}
