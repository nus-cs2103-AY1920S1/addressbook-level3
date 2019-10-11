package seedu.jarvis.commons.exceptions;

/**
 * Signals that a course could not be found.
 */
public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
