package budgetbuddy.model.loan;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

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
     * Returns true if a given string corresponds to a Status value.
     * @param toTest The string to test.
     */
    public static boolean contains(String toTest) {
        return Arrays.stream(Status.values())
                .map(Status::toString)
                .anyMatch(statusStr -> statusStr.equals(toTest));
    }

    /**
     * Gets the icon of a corresponding status.
     * @return The icon as a string.
     */
    public String getStatusIcon() {
        // PAID: tick icon; UNPAID: blank space (or a cross? \u2718)
        return status.equals(PAID.toString()) ? "\u2713" : " ";
    }

    @Override
    public String toString() {
        return status;
    }
}
