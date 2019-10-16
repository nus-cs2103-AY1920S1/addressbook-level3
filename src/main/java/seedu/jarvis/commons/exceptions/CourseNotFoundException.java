package seedu.jarvis.commons.exceptions;

/**
 * Signals that a course could not be found.
 */
public class CourseNotFoundException extends RuntimeException {
    public static final String DEFAULT_COURSE_NOT_FOUND_MESSAGE = "The course could not be found.";

    public CourseNotFoundException() {
        super(DEFAULT_COURSE_NOT_FOUND_MESSAGE);
    }

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
