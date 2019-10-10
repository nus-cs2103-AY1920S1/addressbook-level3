package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wallet's CurrentBalance number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCurrentBalance(String)}
 */
public class CurrentBalance {

    public static final String MESSAGE_CONSTRAINTS =
            "CurrentBalance numbers should only contain numbers and have either 0 or 2 decimal places.\n"
                    + "For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{2,2})?";
    public final float currentBalance;

    /**
     * Constructs a {@code CurrentBalance}.
     *
     * @param currentBalanceStr A valid CurrentBalance string.
     */
    public CurrentBalance(String currentBalanceStr) {
        requireNonNull(currentBalanceStr);
        checkArgument(isValidCurrentBalance(currentBalanceStr), MESSAGE_CONSTRAINTS);
        currentBalance = convert(currentBalanceStr);
    }

    /**
     * Returns true if a given string is a valid CurrentBalance number.
     */
    public static boolean isValidCurrentBalance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Converts the input currentBalance string to a float.
     *
     * @param currentBalanceStr the input String.
     * @return the float representation of the input CurrentBalance.
     */
    private float convert(String currentBalanceStr) {
        float parsedCurrentBalance;
        try {
            parsedCurrentBalance = Float.parseFloat(currentBalanceStr);
        } catch (NumberFormatException e) {
            return 0;
        }
        return parsedCurrentBalance < 0
                ? 0
                : parsedCurrentBalance;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    @Override
    public String toString() {
        return String.format("%.02f", currentBalance);
    }
}
