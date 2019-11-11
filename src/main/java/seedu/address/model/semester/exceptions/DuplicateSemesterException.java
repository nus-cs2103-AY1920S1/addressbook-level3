package seedu.address.model.semester.exceptions;

/**
 * Signals that the operation will result in duplicate semesters (Semesters are considered duplicates if
 * they have the same identity).
 */
public class DuplicateSemesterException extends RuntimeException {
    public DuplicateSemesterException() {
        super("Operation would result in duplicate semesters");
    }
}
