package seedu.jarvis.model.course.exceptions;

/**
 * Signals that a Course is already in the list.
 */
public class CourseAlreadyInListException extends RuntimeException {
    public static final String EXCEPTION_MESSAGE = "The course has already been added!";

    public CourseAlreadyInListException() {
        super(EXCEPTION_MESSAGE);
    }

    public CourseAlreadyInListException(String message) {
        super(message);
    }

    public CourseAlreadyInListException(String message, Throwable cause) {
        super(message, cause);
    }
}
