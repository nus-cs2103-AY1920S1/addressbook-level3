package seedu.address.model.order;

/**
 * Represents an Order's status in the SML.
 * Guarantees: Only 4 valid status.
 */
public enum Status {
    UNSCHEDULED,
    SCHEDULED,
    COMPLETED,
    CANCELLED
}
