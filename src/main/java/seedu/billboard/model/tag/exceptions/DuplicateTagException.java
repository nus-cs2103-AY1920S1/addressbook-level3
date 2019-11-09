package seedu.billboard.model.tag.exceptions;

//@@author waifonglee
/**
 * Signals that the operation will result in duplicate Tag (Tags are considered duplicates if they are
 * considered equals under their @{code equals} method.
 */
public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}
