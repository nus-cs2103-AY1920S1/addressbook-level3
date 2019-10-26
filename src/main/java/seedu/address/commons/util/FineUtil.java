package seedu.address.commons.util;

/**
 * Helper functions for handling fines.
 */
public class FineUtil {

    /**
     * Get the String representation of the fine amount.
     *
     * @param cents Fine amount in cents.
     * @return A string representing the fine amount in dollars.
     */
    public static String centsToDollarString(int cents) {
        double dollarAmt = cents / 100.0;
        return "$" + String.format("%.2f", dollarAmt);
    }

    /**
     * Converts a double with at most 2 decimal places to an int representing the number of cents.
     *
     * @param dollars Amount in dollars.
     * @return Amount in cents.
     */
    public static int dollarsToCents(double dollars) {
        return (int) (dollars * 100.0);
    }
}
