package seedu.address.commons.util;

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
}
