package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Course's title in the course planner component.
 *
 * @author ryanYtan
 */
public class Title {
    public final String title;

    /**
     * Constructs a {@code Title}
     *
     * @param title of the course
     */
    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Title
                && title.equals(((Title) other).title));
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
