package seedu.address.model.usersettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the default maximum renew count of a book.
 * Guarantees: immutable; is valid as declared in {@Link #isValidMaxRenews(String)}
 */
public class MaxRenews {

    public static final int MAX_MAX_RENEWS = 20;
    public static final String MESSAGE_CONSTRAINTS =
            "Maximum renew count should be a positive number and should not exceed " + MAX_MAX_RENEWS;

    private final int maxRenews;

    /**
     * Constructs a {@code RenewCount}.
     *
     * @param maxRenews A valid renew count in String.
     */
    public MaxRenews(String maxRenews) {
        requireNonNull(maxRenews);
        checkArgument(isValidMaxRenews(maxRenews), MESSAGE_CONSTRAINTS);
        this.maxRenews = Integer.parseInt(maxRenews);
    }

    /**
     * Constructs a {@code MaxRenews}.
     *
     * @param maxRenews A valid maximum renew count in integer.
     */
    public MaxRenews(int maxRenews) {
        this.maxRenews = maxRenews;
    }

    public int getMaxRenews() {
        return maxRenews;
    }

    /**
     * Returns true if a given string is a valid maximum renew count.
     */
    public static boolean isValidMaxRenews(String test) {
        requireNonNull(test);
        try {
            int maxRenews = Integer.parseInt(test);
            return maxRenews >= 0 && maxRenews <= MAX_MAX_RENEWS && !test.startsWith("+");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%d", maxRenews);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MaxRenews
                && maxRenews == ((MaxRenews) other).maxRenews);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(maxRenews);
    }
}
