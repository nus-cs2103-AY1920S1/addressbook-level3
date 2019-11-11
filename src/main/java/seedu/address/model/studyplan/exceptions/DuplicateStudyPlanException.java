package seedu.address.model.studyplan.exceptions;

/**
 * Signals that the operation will result in duplicate StudyPlans
 * (StudyPlans are considered duplicates if they have the same identity).
 */
public class DuplicateStudyPlanException extends RuntimeException {
    public DuplicateStudyPlanException() {
        super("Operation would result in duplicate studyPlans");
    }
}
