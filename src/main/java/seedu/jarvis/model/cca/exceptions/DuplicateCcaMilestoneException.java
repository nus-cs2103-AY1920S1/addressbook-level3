package seedu.jarvis.model.cca.exceptions;

public class DuplicateCcaMilestoneException extends RuntimeException {
    public DuplicateCcaMilestoneException() {
            super("Operation would result in duplicate cca milestones!");
        }
}
