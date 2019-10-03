package seedu.jarvis.model.course;

/**
 * Represents a Course's preclusions in the course planner component.
 *
 * @author ryanYtan
 */
public class Preclusion {
    public final String preclusion;

    /**
     * Constructs a {@preclusion Preclusion}
     *
     * @param preclusion of the course
     */
    public Preclusion(String preclusion) {
        this.preclusion = preclusion;
    }

    @Override
    public String toString() {
        return preclusion;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Preclusion
                && preclusion.equals(((Preclusion) other).preclusion));
    }

    @Override
    public int hashCode() {
        return preclusion.hashCode();
    }
}
