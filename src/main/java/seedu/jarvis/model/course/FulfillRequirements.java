package seedu.jarvis.model.course;

/**
 * Represents a Course's requirements that are fulfilled when the user has taken this
 * course in the course planner component.
 *
 * @author ryanYtan
 */
public class FulfillRequirements {
    public final String fulfillRequirements;

    /**
     * Constructs a {@code FulfillRequirements}
     *
     * @param fulfillRequirements of the course
     */
    public FulfillRequirements(String fulfillRequirements) {
        this.fulfillRequirements = fulfillRequirements;
    }

    @Override
    public String toString() {
        return fulfillRequirements;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FulfillRequirements
                && fulfillRequirements.equals(((FulfillRequirements) other).fulfillRequirements));
    }

    @Override
    public int hashCode() {
        return fulfillRequirements.hashCode();
    }
}
