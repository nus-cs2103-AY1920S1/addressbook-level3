package seedu.deliverymans.model.deliveryman.deliverymanstatus;

/**
 * Represents the current status of a deliveryman.
 */
public class StatusTag {

    public final String description;

    public StatusTag(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    /**
     * Returns true if both persons have the same identity. Data fields need not be same.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusTag)) {
            return false;
        }

        StatusTag otherTag = (StatusTag) other;
        return otherTag.getDescription().equals(getDescription());
    }
}
