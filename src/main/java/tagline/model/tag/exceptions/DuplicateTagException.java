package tagline.model.tag.exceptions;

/**
 * Signals that the operation will result in duplicate tags.
 */
public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}
