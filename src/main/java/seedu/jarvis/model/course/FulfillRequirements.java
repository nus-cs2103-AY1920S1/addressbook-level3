package seedu.jarvis.model.course;

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
