package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

public class DaysToExpire {
    /**
     * Represents a Wallet's daysToExpire number in the address book.
     * Guarantees: immutable; is valid as declared in {@link #isValidDaysToExpire(String)}
     */
    public static final String MESSAGE_CONSTRAINTS =
            "DaysToExpire should be a positive integer";
    public static final String VALIDATION_REGEX = "^\\d+$";
    public final int daysToExpire;

    /**
     * Constructs a {@code daysToExpire}.
     *
     * @param daysToExpireStr A valid daysToExpire string.
     */
    public DaysToExpire(String daysToExpireStr) {
        requireNonNull(daysToExpireStr);
        checkArgument(isValidDaysToExpire(daysToExpireStr), MESSAGE_CONSTRAINTS);
        daysToExpire = convert(daysToExpireStr);
    }

    /**
     * Returns true if a given string is a valid daysToExpire number.
     */
    public static boolean isValidDaysToExpire(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Converts the input daysToExpire string to a float.
     *
     * @param daysToExpireStr the input String.
     * @return the float representation of the input daysToExpire.
     */
    private int convert(String daysToExpireStr) {
        return Integer.parseInt(daysToExpireStr);
    }

    public int getDaysToExpire() {
        return daysToExpire;
    }
    @Override
    public String toString() {
        return String.format("%d", daysToExpire);
    }
}
