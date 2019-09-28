package seedu.address.model.order;

/**
 * Represents an Order's status in the SML.
 * Guarantees: Only 4 valid status.
 */
public enum Status {
    UNSCHEDULED,
    SCHEDULED,
    COMPLETED,
    CANCELLED;

    @Override
    public String toString() {
        switch(this) {
        case UNSCHEDULED:
            return "Unscheduled";
        case SCHEDULED:
            return "Scheduled";
        case COMPLETED:
            return "Completed";
        default: // case CANCELLED
            return "Cancelled";
        }
    }
}
