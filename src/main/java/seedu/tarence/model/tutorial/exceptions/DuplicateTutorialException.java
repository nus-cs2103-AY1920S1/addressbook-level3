package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Tutorial.
 */
public class DuplicateTutorialException extends RuntimeException {
    public DuplicateTutorialException() {
        super("Operation would result in duplicate Tutorials");
    }
}
