package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

public class Description {
    public final String description;

    /**
     * Constructs a {@code Description}
     *
     * @param description of the course
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Description
                && description.equals(((Description) other).description));
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
