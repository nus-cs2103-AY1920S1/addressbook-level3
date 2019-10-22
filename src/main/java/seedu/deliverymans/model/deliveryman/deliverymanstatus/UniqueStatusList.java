package seedu.deliverymans.model.deliveryman.deliverymanstatus;

/**
 * A list that contains 3 status tags.
 */
public class UniqueStatusList {
    private final StatusTag availableTag = new StatusTag("AVAILABLE");
    private final StatusTag unavailableTag = new StatusTag("UNAVAILABLE");
    private final StatusTag deliveringTag = new StatusTag("DELIVERING");

    /**
     * Lists all the deliverymen with their respective statuses.
     */
    public StatusTag getAvailableTag() {
        return availableTag;
    }

    public StatusTag getUnavailableTag() {
        return unavailableTag;
    }

    public StatusTag getDeliveringTag() {
        return deliveringTag;
    }

}
