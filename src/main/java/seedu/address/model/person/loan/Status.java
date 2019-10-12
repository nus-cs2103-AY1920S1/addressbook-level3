package seedu.address.model.person.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the status (PAID/UNPAID) of a Loan.
 * Guarantees: immutable; valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status can only be PAID or UNPAID (case-sensitive).";

    public final String status;

    /**
     * Constructs a {@code status}.
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status;
    }

    /**
     * Returns true if the given string is a valid status.
     * @param testStatus The string to test for validity.
     * @return True if valid (string is "PAID" or "UNPAID"), false otherwise.
     */
    public static boolean isValidStatus(String testStatus) {
        return testStatus.equals("PAID") || testStatus.equals("UNPAID");
    }

    /**
     * Gets the icon of a corresponding status.
     * @return The icon as a string.
     */
    public String getStatusIcon() {
        // PAID: tick icon; UNPAID: blank space
        return status.equals("PAID") ? "\u2713" : " ";
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return status.equals(otherStatus.status);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
