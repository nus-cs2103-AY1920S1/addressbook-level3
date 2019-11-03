package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutor's earnings.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount numbers should only contain numbers, and it should have only 2 decimal points";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{2})?$";

    public final String amount;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amt A valid amount.
     */
    public Amount(String amt) {
        requireNonNull(amt);
        System.out.println(amt);
        checkArgument(isValidAmount(amt), MESSAGE_CONSTRAINTS);
        amount = amt;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount.equals(((Amount) other).amount)); // state check
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    /**
     * Adds every amount created into total earnings.
     *
     * @param amt Amount to be added into total earnings.
     * @return New total earnings.
     */
    public Amount addAmount(Amount amt) {
        double localAmt = Double.parseDouble(this.amount);
        System.out.println(localAmt);
        double variableAmt = Double.parseDouble(amt.amount);
        System.out.println(variableAmt);
        double totalAmt = localAmt + variableAmt;
        return new Amount(String.format("%.2f", totalAmt));
    }
}
