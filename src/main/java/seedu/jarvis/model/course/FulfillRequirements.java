package seedu.jarvis.model.course;

import java.util.List;

public class FulfillRequirements {
    public final List<String> fulfillRequirements;

    /**
     * Constructs a {@code FulfillRequirements}
     *
     * @param fulfillRequirements of the course
     */
    public FulfillRequirements(List<String> fulfillRequirements) {
        this.fulfillRequirements = fulfillRequirements;
    }

    @Override
    public String toString() {
        return fulfillRequirements.toString();
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
