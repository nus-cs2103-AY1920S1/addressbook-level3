package seedu.address.model.usersettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the default renew period of a book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRenewPeriod(String)}
 */
public class RenewPeriod {

    private static final int MAX_RENEW_PERIOD = 365;
    public static final String MESSAGE_CONSTRAINTS =
            "Renew period should be a positive non-zero number and should not exceed "
                    + MAX_RENEW_PERIOD + " days.";

    private final int renewPeriod;

    /**
     * Constructs a {@code RenewPeriod}.
     *
     * @param renewPeriod A valid renew period in String.
     */
    public RenewPeriod(String renewPeriod) {
        requireNonNull(renewPeriod);
        checkArgument(isValidRenewPeriod(renewPeriod), MESSAGE_CONSTRAINTS);
        this.renewPeriod = Integer.parseInt(renewPeriod);
    }

    /**
     * Constructs a {@code RenewPeriod}.
     *
     * @param renewPeriod A valid renew period in integer.
     */
    public RenewPeriod(int renewPeriod) {
        this.renewPeriod = renewPeriod;
    }

    public int getRenewPeriod() {
        return renewPeriod;
    }

    /**
     * Returns true if a given string is a valid renew period.
     */
    public static boolean isValidRenewPeriod(String test) {
        requireNonNull(test);
        try {
            int renewPeriod = Integer.parseInt(test);
            return renewPeriod > 0 && renewPeriod <= MAX_RENEW_PERIOD && !test.startsWith("+");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%d", renewPeriod);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RenewPeriod // instanceof handles nulls
                && renewPeriod == (((RenewPeriod) other).renewPeriod)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(renewPeriod);
    }

}
