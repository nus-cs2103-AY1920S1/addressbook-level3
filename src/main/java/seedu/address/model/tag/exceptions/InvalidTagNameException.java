package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation will result in tags with default tag names which are reserved only for {@code DefaultTag}.
 */

public class InvalidTagNameException extends InvalidTagException {

    public InvalidTagNameException() {
        super("Operation would result in user-created tags with reserved default tag names");
    }

}
