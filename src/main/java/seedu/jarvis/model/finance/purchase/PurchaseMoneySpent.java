package seedu.jarvis.model.finance.purchase;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents the description of a purchase in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class PurchaseMoneySpent {

    public static final String MESSAGE_CONSTRAINTS =
            "Money spent on a purchase must have a maximum of 2 decimal places, be a positive value "
                    + "and it should not be blank.";

    public static final String VALIDATION_REGEX = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?";

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private final double purchaseAmount;

    /**
     * Constructs a {@code PurchaseMoneySpent}.
     *
     * @param value A valid purchase money paid.
     */
    public PurchaseMoneySpent(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        purchaseAmount = Double.parseDouble(value);
    }

    /**
     * Returns true if the given double is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    @Override
    public String toString() {
        return df2.format(purchaseAmount);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PurchaseMoneySpent
                && Double.toString(purchaseAmount)
                .equals(Double.toString(((PurchaseMoneySpent) other).purchaseAmount)));
    }
}
