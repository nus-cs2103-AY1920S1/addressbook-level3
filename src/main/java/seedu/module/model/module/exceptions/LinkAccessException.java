package seedu.module.model.module.exceptions;

/**
 * Signals that the program is unable to open the link due to issues on the user's end
 * i.e.unidentifiable OS, browser issues
 */
public class LinkAccessException extends RuntimeException {
    public LinkAccessException(String message) {
        super(message);
    }
}
