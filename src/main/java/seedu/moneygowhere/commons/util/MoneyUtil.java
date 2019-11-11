package seedu.moneygowhere.commons.util;

//@@author jonathantjendana

/**
 * Contains utility methods used for parsing large decimal to a more readable string
 */
public class MoneyUtil {

    private static char[] suffix = new char[]{'k', 'M', 'B', 'T'};

    //@@author jonathantjendana-reused
    //Reused from https://stackoverflow.com/a/4753866 with minor modifications
    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invocation.
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    private static String format(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0; //true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000 // this determines the class, i.e. 'k', 'm' etc
                ? ((d > 99.9 || isRound || (!isRound && d > 9.99) ? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + suffix[iteration])
                : format(d, iteration + 1));
    }
    //@@author jonathantjendana
    /**
     * Formats a large decimal to a more readable string
     * e.g 100000 = 100k
     * @param number
     * @return
     */
    public static String format(double number) {
        return number > 1000
                ? format(number, 0)
                : String.format("%.2f", number);
    }
}
