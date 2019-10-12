package seedu.jarvis.model.course;

/**
 * Represents a Course's faculty in the course planner component.
 *
 * @author ryanYtan
 */
public class Faculty {
    public final String faculty;

    /**
     * Constructs a {@code Faculty}
     *
     * @param faculty of the course
     */
    public Faculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return faculty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Faculty
                && faculty.equals(((Faculty) other).faculty));
    }

    @Override
    public int hashCode() {
        return faculty.hashCode();
    }
}
