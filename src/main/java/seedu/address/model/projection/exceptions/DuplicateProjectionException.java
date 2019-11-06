package seedu.address.model.projection.exceptions;

/**
 * Signals that the operation will result in duplicate Projections
 * (Projections are considered duplicate if they have the same {@code Date}).
 */
public class DuplicateProjectionException extends RuntimeException {
    public DuplicateProjectionException() {
        super("Operation would result in duplicate Projections");
    }
}
