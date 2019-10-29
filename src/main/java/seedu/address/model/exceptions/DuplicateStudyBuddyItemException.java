package seedu.address.model.exceptions;

/**
 * Signals that the operation will result in duplicate StudyBuddyItems
 * (StudyBuddyItems are considered duplicates if they have the same fields).
 */

public class DuplicateStudyBuddyItemException extends RuntimeException {
    public DuplicateStudyBuddyItemException() {
        super("Operation would result in duplicate StudyBuddy items.");
    }
}
