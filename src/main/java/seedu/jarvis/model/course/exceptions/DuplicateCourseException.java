package seedu.jarvis.model.course.exceptions;

/**
 * Signals that a Course is already in the list.
 */
public class DuplicateCourseException extends RuntimeException {
    public static final String EXCEPTION_MESSAGE = "Courses must be unique.";

    public DuplicateCourseException() {
        super(EXCEPTION_MESSAGE);
    }

    public DuplicateCourseException(String message) {
        super(message);
    }

    public DuplicateCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
