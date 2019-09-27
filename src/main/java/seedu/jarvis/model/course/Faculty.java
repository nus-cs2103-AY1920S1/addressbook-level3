package seedu.jarvis.model.course;

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
