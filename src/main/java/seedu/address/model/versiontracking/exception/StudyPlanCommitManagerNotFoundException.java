package seedu.address.model.versiontracking.exception;

/**
 * Exception for when a StudyPlanCommitManager cannot be found by the given index.
 */
public class StudyPlanCommitManagerNotFoundException extends RuntimeException {
    public StudyPlanCommitManagerNotFoundException() {
        super("StudyPlanCommitManager of this index is not found!");
    }
}
