package seedu.jarvis.model.course.exceptions;

/**
 * Signals that a Course is not in the list.
 */
public class CourseNotInListException extends RuntimeException {
    public static final String EXCEPTION_MESSAGE = "The course could not be found!";

    public CourseNotInListException() {
        super(EXCEPTION_MESSAGE);
    }

    public CourseNotInListException(String message) {
        super(message);
    }

    public CourseNotInListException(String message, Throwable cause) {
        super(message, cause);
    }
}
