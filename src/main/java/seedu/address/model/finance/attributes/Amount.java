package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the amount associated with a log entry in the finance log.
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts should be numerical (up to 2 decimals allowed), should be more than 0 and should not be blank";

    /*
     * The first character of the amount must not be a whitespace,
     * at with most 2 decimals places,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\d]+|[\\d]+\\.[\\d]{1,2}";

    public final double amount;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = Double.parseDouble(amount);
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        boolean matchesRegex = test.matches(VALIDATION_REGEX);
        boolean isMoreThanZero = !test.matches("^0|0.0|0.00$");
        return matchesRegex && isMoreThanZero;
    }


    @Override
    public String toString() {
        String strAmount = Double.toString(amount);

        // Ensure 2 decimals places
        String oneDecimalRegex = "[\\d]+\\.[0-9]$";
        if (strAmount.matches(oneDecimalRegex)) {
            strAmount = strAmount + "0";
        }

        return strAmount;
    }

    /**
     * Returns if amount is bigger, smaller or equal to another amount
     */
    public int compareTo(Amount other) {
        double difference = amount - other.amount;
        return difference > 1 ? 1 : (difference < 0 ? -1 : 0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && (Double.compare(amount, ((Amount) other).amount)) == 0); // state check
    }

    @Override
    public int hashCode() {
        return Double.toString(amount).hashCode();
    }

}
