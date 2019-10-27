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
}
