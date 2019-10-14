package seedu.address.model.person.loan.stub;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the amount of a loan.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(long)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should be non-negative.";

    public final long amount;

    public Amount(long amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    public static boolean isValidAmount(long testAmount) {
        return testAmount >= 0;
    }

    @Override
    public String toString() {
        return Long.toString(amount);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Amount && amount == ((Amount) other).amount);
    }
}
