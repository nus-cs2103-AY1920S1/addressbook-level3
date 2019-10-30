package seedu.address.model.classroom.exceptions;

/**
 * Signals that the operation will result in duplicate Classrooms (Classrooms are considered duplicates if they have the
 * same identity).
 */
public class DuplicateClassroomException extends RuntimeException {
    public DuplicateClassroomException() {
        super("Operation would result in duplicate classrooms");
    }
}
