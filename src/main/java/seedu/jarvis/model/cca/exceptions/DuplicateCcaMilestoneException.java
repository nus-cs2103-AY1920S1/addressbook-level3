package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the operation would result in duplicate CcaMilestones.
 */
public class DuplicateCcaMilestoneException extends RuntimeException {
    public DuplicateCcaMilestoneException() {
            super("Operation would result in duplicate cca milestones!");
    }
}
