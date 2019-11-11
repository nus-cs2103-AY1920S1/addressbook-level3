package seedu.deliverymans.model.deliveryman.deliverymanstatus;

import static java.util.Objects.requireNonNull;

/**
 * A tag that represents the current status of a deliveryman.
 */
public class StatusTag {

    public final String description;

    /**
     * Constructs a {@code StatusTag}.
     *
     * @param description A description.
     */
    public StatusTag(String description) {
        requireNonNull(description);

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
     * Returns true if both tags have the same description.
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

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
