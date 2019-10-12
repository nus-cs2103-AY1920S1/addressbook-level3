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

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
