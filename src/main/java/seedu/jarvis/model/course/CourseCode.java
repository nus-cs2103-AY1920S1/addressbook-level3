package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Course's course code in the course planner component.
 *
 * @author ryanYtan
 */
public class CourseCode {
    public static final String MESSAGE_CONSTRAINTS = "The course code is usually a series of"
            + "two or three letters, followed by four numbers. Sometimes, another letter at"
            + "the end of the course code is used to refer to similar versions of the particular"
            + "course";

    public final String code;

    /**
     * Constructs a {@code CourseCode}
     *
     * @param code of the course
     */
    public CourseCode(String code) {
        requireNonNull(code);
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CourseCode
                && code.equals(((CourseCode) other).code));
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
