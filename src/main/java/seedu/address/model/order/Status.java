package seedu.address.model.order;

/**
 * Represents an Order's status in the SML.
 * Guarantees: Only 4 valid status.
 */
public enum Status {
    UNSCHEDULED("Unscheduled"),
    SCHEDULED("Scheduled"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    public static final String MESSAGE_CONSTRAINTS =
            "status should only be unscheduled, scheduled, completed, cancelled ";

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
