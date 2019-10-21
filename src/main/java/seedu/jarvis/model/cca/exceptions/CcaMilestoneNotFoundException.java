package seedu.jarvis.model.cca.exceptions;

public class CcaMilestoneNotFoundException extends RuntimeException {
    public CcaMilestoneNotFoundException() {
        super("Cca Milestone is not found");
    }
}
