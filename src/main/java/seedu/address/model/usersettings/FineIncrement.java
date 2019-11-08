package seedu.address.model.usersettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the default fine amount in cents of a overdue book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFineIncrement(String)}
 */
public class FineIncrement {

    private static final int MAX_FINE_INCREMENT = 100000;
    public static final String MESSAGE_CONSTRAINTS =
            "Fine increment should be a positive number and should not exceed " + MAX_FINE_INCREMENT + " cents.";

    private final int fineIncrement;

    /**
     * Constructs a {@code FineIncrement}.
     *
     * @param fineIncrement A valid fine increment in String.
     */
    public FineIncrement(String fineIncrement) {
        requireNonNull(fineIncrement);
        checkArgument(isValidFineIncrement(fineIncrement), MESSAGE_CONSTRAINTS);
        this.fineIncrement = Integer.parseInt(fineIncrement);
    }

    /**
     * Constructs a {@code FineIncrement}.
     *
     * @param fineIncrement A valid fine increment in integer.
     */
    public FineIncrement(int fineIncrement) {
        this.fineIncrement = fineIncrement;
    }

    public int getFineIncrement() {
        return fineIncrement;
    }

    /**
     * Returns true if a given string is a valid fine amount.
     */
    public static boolean isValidFineIncrement(String test) {
        requireNonNull(test);
        try {
            int amount = Integer.parseInt(test);
            return amount >= 0 && amount <= MAX_FINE_INCREMENT && !test.startsWith("+");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%d", fineIncrement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FineIncrement // instanceof handles nulls
                && fineIncrement == (((FineIncrement) other).fineIncrement)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(fineIncrement);
    }
}
