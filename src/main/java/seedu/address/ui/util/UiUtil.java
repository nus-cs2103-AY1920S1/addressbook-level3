package seedu.address.ui.util;

import seedu.address.model.activity.Amount;

/**
 * Contains utility methods for formatting that are common to multiple UI components.
 */
public final class UiUtil {
    /**
     * Returns a {@code String} describing the number of participants in this activity.
     * @param count An {@code int} specifying the number of participants.
     * @return A formatted {@code String}.
     */
    public static String formatParticipantCount(int count) {
        assert count >= 0 : "Number of participants in an activity is non-negative!";
        return count == 1 ? "1 participant" : count + " participants";
    }

    /**
     * Formats an amount in dollars to two significant figures, contracting it if the amount is
     * less than $0.01.
     * @param amount A {@code double} specifying the amount to format.
     * @return The formatted {@code String} for this amount.
     */
    public static String formatAmount(double amount) {
        // Threshold below which to show contracted amount
        double threshold = Amount.MIN;

        if (amount < threshold) {
            return "<$0.01";
        } else {
            return String.format("$%.2f", amount);
        }
    }
}
