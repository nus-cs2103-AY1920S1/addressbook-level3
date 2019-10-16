package budgetbuddy.model.person.loan;

import static java.util.Objects.requireNonNull;

/**
 * Represents the status (PAID/UNPAID) of a Loan.
 * Guarantees: immutable
 */
public enum Status {
    PAID("PAID"),
    UNPAID("UNPAID");

    public static final String MESSAGE_CONSTRAINTS =
            "Status can only be PAID or UNPAID (case-sensitive).";

    public final String status;

    /**
     * Constructs a {@code status}.
     * @param status A valid status.
     */
    Status(String status) {
        requireNonNull(status);
        this.status = status;
    }

    /**
     * Gets the icon of a corresponding status.
     * @return The icon as a string.
     */
    public String getStatusIcon() {
        // PAID: tick icon; UNPAID: blank space
        return status.equals(PAID.toString()) ? "\u2713" : "\u2718";
    }

    @Override
    public String toString() {
        return status;
    }
}
