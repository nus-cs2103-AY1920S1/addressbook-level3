package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the {@code CcaMilestone} is not found.
 */
public class CcaMilestoneNotFoundException extends RuntimeException {
    public CcaMilestoneNotFoundException() {
        super("Cca Milestone is not found");
    }
}
