package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation would result in duplicate tags (Tags are considered duplicates if they have the same
 * name).
 */
public class DuplicateTagException extends InvalidTagException {

    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }

}
