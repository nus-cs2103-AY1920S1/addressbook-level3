package seedu.address.ics;

/**
 * Exception is created within the IcsParser class when something wrong happens while a .ics file is being imported.
 */
public class IcsException extends Exception {
    public IcsException(String message) {
        super(message);
    }
}
