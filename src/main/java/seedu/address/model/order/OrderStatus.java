package seedu.address.model.order;

/**
 * Represents an Order's order status in the SML.
 * Guarantees: Only 3 valid status.
 */
public enum OrderStatus {
    UNSCHEDULED,
    SCHEDULED,
    COMPLETED
}
