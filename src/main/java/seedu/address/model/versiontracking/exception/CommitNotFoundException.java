package seedu.address.model.versiontracking.exception;

/**
 * Exception for when a commit cannot be found by the given index.
 */
public class CommitNotFoundException extends RuntimeException {
    public CommitNotFoundException() {
        super("Commit of this index is not found!");
    }
}
