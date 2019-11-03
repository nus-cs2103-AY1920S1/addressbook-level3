package seedu.address.diaryfeature.logic.parser.exceptions;

public class DetailParseException extends Exception {
    private final String ERROR_MESSAGE = "Details have to at least 8 characters, and they can only be alphanumeric!";

    public String toString() {
        return ERROR_MESSAGE;
    }
}
