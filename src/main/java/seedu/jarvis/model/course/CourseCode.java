package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

public class CourseCode {
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
