package seedu.jarvis.model.financetracker.purchase;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of a purchase in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class PurchaseMoneySpent {

    public static final String MESSAGE_CONSTRAINTS =
            "Money spent on installments should be taken as doubles, "
                    + "and it should not be blank";

    public static final String MONEY_CONSTRAINTS =
            "Money spent cannot be equal to or less than 0.";

    public static final String VALIDATION_REGEX = "[0-9]{1,13}(\\.[0-9]*)?";

    public final double purchaseAmount;

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
        return Double.toString(purchaseAmount);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PurchaseMoneySpent
                && Double.toString(purchaseAmount)
                .equals(Double.toString(((PurchaseMoneySpent) other).purchaseAmount)));
    }
}
