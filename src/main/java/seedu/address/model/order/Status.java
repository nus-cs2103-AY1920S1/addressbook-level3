package seedu.address.model.order;

/**
 * Represents an Order's status in the SML.
 * Guarantees: Only 3 valid status.
 */
public enum Status {
    UNSCHEDULED,
    SCHEDULED,
    COMPLETED
}
