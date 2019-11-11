package seedu.address.ics;

//@@author marcusteh1238
/**
 * Exception is created within the IcsParser class when something wrong happens while an ICS file is being imported.
 */
public class IcsException extends Exception {
    public IcsException(String message) {
        super(message);
    }
}
