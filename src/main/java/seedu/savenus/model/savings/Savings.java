package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Acts as the amount of money to be added into the {@code SavingsAccount} of the user
 * and also the amount of money to deduct from the {@code Wallet} of the user.
 */
public class Savings {

    public static final String MESSAGE_CONSTRAINTS =
            "Please provide a savings amount with 0 or 2 decimal places.\n"
            + "For example: 1.50 or 200";

    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

    // Identity fields of a saving.
    private final String savingsAmount; // the amount to be saved.

    /**
     * TODO @FATCLARENCE
     * Add time stamp of savings.
     */

    // Default starting savings amount.
    public Savings() {
        savingsAmount = "0.00";
    }

    public Savings(String savings) {
        requireNonNull(savings);
        checkArgument(isValidSaving(savings), MESSAGE_CONSTRAINTS);
        savingsAmount = convert(savings);
    }

    /**
     * Returns true if a given string is a valid saving representation.
     */
    public static boolean isValidSaving(String saving) {
        if (saving.matches(VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the input savings has decimal digits or not.
     * @param savingsAmount the input String.
     * @return true if the input savings has no decimal digits.
     */
    public boolean isPerfectNumber(String savingsAmount) {
        try {
            Integer.parseInt(savingsAmount);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Converts the input savings to a valid string representation.
     * @param savingsAmount the input String.
     * @return the correct String representation of the input savings.
     */
    public String convert(String savingsAmount) {
        if (isPerfectNumber(savingsAmount)) {
            return String.format("%d.00", Integer.parseInt(savingsAmount));
        } else {
            return String.format("%.2f", Double.parseDouble(savingsAmount));
        }
    }

    @Override
    public String toString() {
        return savingsAmount;
    }
}
